package UniP_server_chat.Unip_party_chat.domain.chatRoom.service;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.MakeChatRooms;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.repository.ChatRoomRepository;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service.ChatRoomParticipantService;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import UniP_server_chat.Unip_party_chat.domain.chatStore.service.ChatStoreService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.domain.party.repository.PartyRepository;
import UniP_server_chat.Unip_party_chat.domain.party.dto.PartyDto;
import UniP_server_chat.Unip_party_chat.domain.party.entity.Party;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ChatRoomErrorCode;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.PartyErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatStoreService chatStoreService;
    private final ChatRoomParticipantService chatRoomParticipantService;
    private final PartyRepository partyRepository;
    @Transactional
    public List<ChatRoomsDto> getChatRooms() {
        ChatStore chatStore = chatStoreService.createOrUseChatStore();

        return chatRoomRepository.findAllChatRoomsByChatStore(chatStore)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND));
    }

    @Transactional
    public void makeChatRoomInit(MakeChatRooms makeChatRooms,Member member) {
        PartyDto partyDto = makeChatRooms.getPartyDto();

        Party party = partyRepository.findById(partyDto.partyId())
                .orElseThrow(() -> new CustomException(PartyErrorCode.PARTY_NOT_FOUND));

        ChatRoom chatRoom = ChatRoom.builder()
                .title(partyDto.title())
                .party(party)
                .isDeleted(false)
                .build();

        ChatRoom savedChatRoom = chatRoomRepository.save(chatRoom);
        chatRoomParticipantService.makeOneChatRoomParticipant(member, savedChatRoom);
    }

    @Transactional
    public void participateInPartyChat(Long partyId, Member member) {
        ChatRoom foundedChatRoom = chatRoomRepository.findChatRoomByPartyId(partyId)
                .orElseThrow(() -> new CustomException(PartyErrorCode.PARTY_NOT_FOUND));

        chatRoomParticipantService.makeOneChatRoomParticipant(member, foundedChatRoom);
    }

    public ChatRoom findById(UUID roomId) {
        return chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new CustomException(ChatRoomErrorCode.CHAT_ROOM_NOT_FOUND));
    }

}
