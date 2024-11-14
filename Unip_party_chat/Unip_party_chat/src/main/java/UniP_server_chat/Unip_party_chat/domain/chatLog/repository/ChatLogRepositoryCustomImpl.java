package UniP_server_chat.Unip_party_chat.domain.chatLog.repository;


import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.QChatLog;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static UniP_server_chat.Unip_party_chat.domain.chatLog.entity.QChatLog.*;

@RequiredArgsConstructor
public class ChatLogRepositoryCustomImpl implements ChatLogRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<ChatLogDto>> findById(UUID roomId, Pageable pageable,Long startChatLogId) {
        List<ChatLogDto> findChatLogs = queryFactory
                .select(Projections.constructor(ChatLogDto.class,
                        chatLog.member.name,
                        chatLog.content
                ))
                .from(chatLog)
                .where(chatLog.chatRoom.id.eq(roomId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .where(chatLog.id.goe(startChatLogId))
                .fetch();

        return Optional.ofNullable(findChatLogs);
    }

    @Override
    public Long findMaxIdByRoomId(UUID roomId) {
        Long maxId = queryFactory
                .select(chatLog.id.max())
                .from(chatLog)
                .where(chatLog.chatRoom.id.eq(roomId))
                .fetchOne();

        return maxId;
    }
}
