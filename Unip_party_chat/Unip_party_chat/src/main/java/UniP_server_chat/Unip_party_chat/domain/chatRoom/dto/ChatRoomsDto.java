package UniP_server_chat.Unip_party_chat.domain.chatRoom.dto;


import lombok.Builder;

import java.util.UUID;

@Builder
public record ChatRoomsDto(UUID id,String title) {
}
