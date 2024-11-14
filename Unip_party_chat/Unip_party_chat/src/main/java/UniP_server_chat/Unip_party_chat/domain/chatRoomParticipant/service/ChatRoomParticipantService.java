package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service;

import UniP_server_chat.Unip_party_chat.domain.chatLog.repository.ChatLogRepository;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.repository.ChatRoomRepository;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.ChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository.ChatRoomParticipantRepository;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.chatStore.service.ChatStoreService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatRoomErrorCode;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatRoomParticipantErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomParticipantService {
    private final ChatRoomParticipantRepository chatRoomParticipantRepository;
    private final ChatStoreService chatStoreService;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatLogRepository chatLogRepository;

    @Transactional
    public void makeChatRoomParticipants(List<Member> members, ChatRoom chatRoom, boolean isFirst) {
        if (isFirst) {
            addParticipant(chatRoom, null);
        }

        members.forEach(member -> addParticipant(chatRoom, member));
    }

    @Transactional
    public void makeOneChatRoomParticipant(Member member, ChatRoom chatRoom) {
        addParticipant(chatRoom, member);
    }

    @Transactional
    public void deleteChatRoomParticipant(Member member, UUID roomId) {
        // 채팅방에 현재 참여하고 있는 참여자의 수를 가져온다.
        Long counts = getChatRoomParticipantCounts(roomId);

        // 만약 채팅방에 참여하고 있는 인원이 1명이면 채팅방과 채팅인원 둘다 제거한다.
        deleteChatRoomParticipant(member, roomId, counts);
    }

    private void deleteChatRoomParticipant(Member member, UUID roomId, Long counts) {
        chatRoomParticipantRepository.deleteChatRoomParticipantByRoomIdAndMember(roomId, member);

        if (counts == 1) {
            ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                    .orElseThrow(() -> new CustomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND));

            chatRoom.changeIsDeleted(true);
            chatRoomRepository.save(chatRoom);
        }
    }

    private Long getChatRoomParticipantCounts(UUID roomId) {
        Long counts = chatRoomParticipantRepository.getCountsAtSpecificChatRoom(roomId);

        if (counts == null) {
            throw new CustomException(ChatRoomParticipantErrorCode.CANT_DELETE);
        }

        return counts;
    }

    private void addParticipant(ChatRoom chatRoom, Member member) {
        ChatStore chatStore = (member != null)
                ? chatStoreService.createOrUseChatStore(member)
                : chatStoreService.createOrUseChatStore();


        // 채팅방에 참여할 때 화면에 채팅기록을 출력할 기준이 될 필드
        Long maxId = setMaxId(chatLogRepository.findMaxIdByRoomId(chatRoom.getId()));

        ChatRoomParticipant chatRoomParticipant = ChatRoomParticipant.builder()
                .chatStore(chatStore)
                .chatRoom(chatRoom)
                .startChatLogId(maxId)
                .build();

        chatRoomParticipantRepository.save(chatRoomParticipant);
    }

    private static Long setMaxId(Long maxId) {
        if (maxId == null) {
            maxId = 1L;
        } else {
            maxId += 1;
        }
        return maxId;
    }
}
