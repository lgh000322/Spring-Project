package UniP_server_chat.Unip_party_chat.domain.chatLog.controller;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatMessage;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.ChatLogService;
import UniP_server_chat.Unip_party_chat.domain.chatLog.service.MessageProducer;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name = "채팅기록", description = "채팅 기록 관련 API")
public class ChatLogController {
    private final MessageProducer messageProducer;
    private final ChatLogService chatLogService;

    @MessageMapping("/chat/send/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage sendMessage(@DestinationVariable UUID roomId,
                                   @Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat/addUser/{roomId}")
    @SendTo("/topic/room/{roomId}")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor,
                               @DestinationVariable UUID roomId) {

        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    //메시지 송신을 http로 할 경우
    @PostMapping("/topic/room/{roomId}")
    public ResponseEntity<ResponseDto<?>> sendChat(@PathVariable(name = "roomId") UUID roomId, @RequestBody ChatMessage chatMessage) {
        chatMessage.setRoomId(roomId);

        messageProducer.sendMessageToServer(roomId, chatMessage);//RabbitMQ에 메시지 전송(로드 밸런서가 리버스 프록시로 사용중인 다른 서버에도 요청을 보냄)

        messageProducer.sendMessage(roomId, chatMessage); // RabbitMQ에 메시지 전송(데이터베이스 저장을 비동기로 수행)

        return ResponseEntity.ok().body(ResponseDto.of("메시지 전송 성공", null));
    }

    @GetMapping("/chat/logs/{roomId}")
    @Operation(summary = "채팅 기록 조회", description = "특정 채팅방의 채팅 기록을 가져온다.")
    public ResponseEntity<ResponseDto<?>> getChatLogs(@PathVariable(name = "roomId") UUID roomId,
                                                      @RequestParam Pageable pageable) {
        return ResponseEntity.ok().body(ResponseDto.of("채팅 기록 조회 성공.", chatLogService.findById(roomId, pageable)));
    }
}
