package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.ChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.QChatStore;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import static UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom.chatRoom;
import static UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant.chatRoomParticipant;
import static UniP_server_chat.Unip_party_chat.domain.chatStore.entity.QChatStore.chatStore;
import static UniP_server_chat.Unip_party_chat.domain.member.entity.QMember.member;

@RequiredArgsConstructor
public class ChatRoomParticipantRepositoryCustomImpl implements ChatRoomParticipantRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long getCountsAtSpecificChatRoom(UUID roomId) {
        return queryFactory
                .select(chatRoomParticipant.count())
                .from(chatRoomParticipant)
                .leftJoin(chatRoom).on(chatRoomParticipant.chatRoom.id.eq(chatRoom.id))
                .where(chatRoomParticipant.chatRoom.id.eq(roomId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne();
    }

    @Override
    public void deleteChatRoomParticipantByRoomIdAndMember(UUID roomId, Member member) {
        queryFactory
                .delete(chatRoomParticipant)
                .where(chatRoomParticipant.chatRoom.id.eq(roomId)
                        .and(chatRoomParticipant.chatStore.member.id.eq(member.getId())))
                .execute();
    }

    @Override
    public Long findChatRoomParticipantByMemberId(Long memberId) {
        Long startChatLogId = queryFactory
                .select(chatRoomParticipant.startChatLogId)
                .from(chatRoomParticipant)
                .leftJoin(chatStore).on(chatRoomParticipant.chatStore.id.eq(chatStore.id))
                .leftJoin(member).on(chatStore.member.id.eq(member.id))
                .where(member.id.eq(memberId))
                .fetchOne();

        return startChatLogId;
    }
}
