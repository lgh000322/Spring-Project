package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.entity.ChatRoomParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomParticipantRepository extends JpaRepository<ChatRoomParticipant, Long>,ChatRoomParticipantRepositoryCustom {
}
