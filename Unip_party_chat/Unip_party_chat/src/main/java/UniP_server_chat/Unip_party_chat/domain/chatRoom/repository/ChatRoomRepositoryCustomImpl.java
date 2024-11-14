package UniP_server_chat.Unip_party_chat.domain.chatRoom.repository;


import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.QChatRoom.chatRoom;
import static UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.QChatRoomParticipant.*;
import static UniP_server_chat.Unip_party_chat.domain.chatStore.entity.QChatStore.chatStore;

@RequiredArgsConstructor
public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<ChatRoomsDto>> findAllChatRoomsByChatStore(ChatStore chatStoreParam) {
        List<ChatRoomsDto> result = queryFactory
                .select(Projections.constructor(ChatRoomsDto.class,
                        chatRoom.id,
                        chatRoom.title))
                .from(chatRoom)
                .leftJoin(chatRoomParticipant).on(chatRoomParticipant.chatRoom.id.eq(chatRoom.id))
                .leftJoin(chatStore).on(chatRoomParticipant.chatStore.id.eq(chatStore.id))
                .where(chatStore.id.eq(chatStoreParam.getId())
                        .and(chatRoom.isDeleted.eq(false)))
                .fetch();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<ChatRoom> findChatRoomByPartyId(Long partyId) {
        ChatRoom findChatRoom = queryFactory
                .select(chatRoom)
                .from(chatRoom)
                .where(chatRoom.party.id.eq(partyId))
                .fetchOne();

        return Optional.ofNullable(findChatRoom);
    }
}
