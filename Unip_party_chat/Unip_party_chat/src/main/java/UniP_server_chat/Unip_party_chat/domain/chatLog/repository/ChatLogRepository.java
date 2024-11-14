package UniP_server_chat.Unip_party_chat.domain.chatLog.repository;

import UniP_server_chat.Unip_party_chat.domain.chatLog.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long>,ChatLogRepositoryCustom {
}
