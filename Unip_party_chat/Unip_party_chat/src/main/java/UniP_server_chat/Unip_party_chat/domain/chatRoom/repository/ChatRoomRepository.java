package UniP_server_chat.Unip_party_chat.domain.chatRoom.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, UUID>,ChatRoomRepositoryCustom {
}
