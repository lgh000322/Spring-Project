package UniP_server_chat.Unip_party_chat.domain.chatLog.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLog;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.service.ChatRoomService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatLogErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MessageConsumer {
    private final ChatRoomService chatRoomService;
    private final ObjectMapper objectMapper;
    private final CustomMemberService customMemberService;
    private final List<ChatLog> chatLogs = new ArrayList<>();
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatLogService chatLogService;
    private static final int BATCH_SIZE = 100; // 배치 크기 설정

    // 브로드캐스트 메시지 수신 처리 (STOMP로 클라이언트에게 전달)
    @RabbitListener(queues = "#{broadcastQueue.name}")
    public void handleBroadcastMessage(ChatMessage message) {
        String destination = "/topic/chat/" + message.getRoomId();
        messagingTemplate.convertAndSend(destination, message);
    }

    // Storage 큐의 메시지 배치 처리
    /**
     *  Todo: 분산된 환경에서 데이터베이스 저장 순서를 맞춰야 한다. + 채팅방마다 분산된 큐를 구독해야한다
     *   ex) 만약 서버1이 메시지 큐에서 채팅방 1번의 채팅로그 데이터 1,3,6,4를 가져오고
     *           서버2가 메시지 큐에서 채팅방 1번의 채팅로그 데이터 5,2,8,7을 가져왔다면,
     *        각 서버에서의 채팅로그 데이터는 1,3,4,6 과 2,5,7,8이 된다. ( => 서버 자체적으로 타임스탬프 기준 정렬을 하기 때문에)
     *        하지만 데이텁베이스에 저장할때는 1,2,3,4,5,6,7,8이 될지 1,3,4,6,2,5,7,8이 될지 알 수 없다.
     *
     *   해결 방법 1) 데이터베이스엔 그냥 저장하되, 나중에 조회할 때 타임스탬프와 방번호를 기준으로 채팅로그 데이터를 조회한다. => 클러스터링 인덱스 미사용
     *   해결 방법 2) 그냥 mongodb같은 nosql을 사용한다 => 각 서버별로 저장되는 시점은 보장할 순 없지만 데이터베이스상의 레코드 순서는 보장할 수 있다.
     */

    @RabbitListener(
            queues = "chat.storage.queue",
            containerFactory = "batchMessageListenerContainer"
    )
    public void handleStorageMessages(List<Message> messages) {
        List<ChatMessage> chatMessages = convertAndSortMessage(messages);
        List<ChatLog> beSavedChatLogs = setBeSavedChatLogs(chatMessages);
        chatLogService.bulkSave(beSavedChatLogs);
    }

    private List<ChatLog> setBeSavedChatLogs(List<ChatMessage> chatMessages) {
        List<ChatLog> beSavedChatLogs = new ArrayList<>();

        for (ChatMessage chatMessage : chatMessages) {
            ChatRoom chatRoom = chatRoomService.findById(chatMessage.getRoomId());
            Member member = customMemberService.loadUserByUsername(chatMessage.getSender());

            ChatLog chatLog = ChatLog.builder()
                    .chatRoom(chatRoom)
                    .member(member)
                    .content(chatMessage.getContent())
                    .build();

            beSavedChatLogs.add(chatLog);
        }
        return beSavedChatLogs;
    }

    private List<ChatMessage> convertAndSortMessage(List<Message> messages) {
        List<ChatMessage> chatMessages = messages.stream()
                .map(this::convertToChatMessage)
                .sorted(Comparator.comparing(ChatMessage::getSendTime))
                .collect(Collectors.toList());
        return chatMessages;
    }

    private ChatMessage convertToChatMessage(Message message) {
        // 메시지의 바이트 배열을 얻고 이를 ChatMessage 객체로 역직렬화
        try {
            // 메시지의 바이트 배열을 ObjectMapper를 사용해 ChatMessage 객체로 변환
            byte[] body = message.getBody();
            return objectMapper.readValue(body, ChatMessage.class);
        } catch (IOException e) {
            // 예외 처리 (디버깅용 로그)
            throw new CustomException(ChatLogErrorCode.CHAT_LOG_MAPPING);
        }
    }

    /**
     * 사용되지 않을 수 있음
     * @param chatMessage
     */
   // @RabbitListener(queues = "chat.queue", concurrency = "5-10")
    public void receiveMessage(ChatMessage chatMessage) {
        ChatRoom chatRoom = chatRoomService.findById(chatMessage.getRoomId());
        Member member = customMemberService.loadUserByUsername(chatMessage.getSender());

        ChatLog chatLog = ChatLog.builder()
                .chatRoom(chatRoom)
                .member(member)
                .content(chatMessage.getContent())
                .build();

        synchronized (chatLogs) {
            chatLogs.add(chatLog);
            if (chatLogs.size() >= BATCH_SIZE) {
                chatLogService.bulkSave(chatLogs);
            }
        }
    }

    /**
     * 사용되지 않을 수 있음
     * @param chatMessage
     */
 //   @RabbitListener(queues = "chat.message.queue")
    public void sendMessageToServer(ChatMessage chatMessage) {
        String destination = "/topic/room/" + chatMessage.getRoomId();
        messagingTemplate.convertAndSend(destination, chatMessage);
    }

    /**
     * 사용되지 않을 수 있음.
     */
    // 남아있는 메시지 저장
//    @Scheduled(fixedDelay = 1000) // 1초마다 남아있는 메시지 저장
    public void saveRemainingChatLogs() {
        synchronized (chatLogs) {
            if (!chatLogs.isEmpty()) {
                chatLogService.bulkSave(chatLogs);
            }
        }
    }
}
