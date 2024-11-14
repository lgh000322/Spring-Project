package UniP_server_chat.Unip_party_chat.domain.chatLog.repository;

import UniP_server_chat.Unip_party_chat.domain.chatLog.dto.ChatLogDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ChatLogRepositoryCustom {

    Optional<List<ChatLogDto>> findById(UUID roomId, Pageable pageable,Long startChatLogId);

    Long findMaxIdByRoomId(UUID roomId);

}
