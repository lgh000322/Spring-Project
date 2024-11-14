package UniP_server_chat.Unip_party_chat.domain.chatRoom.repository;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.ChatRoomsDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatStore.entity.ChatStore;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {
    Optional<List<ChatRoomsDto>> findAllChatRoomsByChatStore(ChatStore chatStore);

    Optional<ChatRoom> findChatRoomByPartyId(Long partyId);
}
