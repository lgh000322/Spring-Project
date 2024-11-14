package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.ChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;

import java.util.UUID;

public interface ChatRoomParticipantRepositoryCustom {
    Long getCountsAtSpecificChatRoom(UUID roomId);

    void deleteChatRoomParticipantByRoomIdAndMember(UUID roomId, Member member);

    Long findChatRoomParticipantByMemberId(Long memberId);
}
