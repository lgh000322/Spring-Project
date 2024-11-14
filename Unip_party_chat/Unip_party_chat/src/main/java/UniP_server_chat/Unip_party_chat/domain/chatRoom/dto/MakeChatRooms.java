package UniP_server_chat.Unip_party_chat.domain.chatRoom.dto;

import UniP_server_chat.Unip_party_chat.domain.party.dto.PartyDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Schema(description = "채팅방 생성 DTO")
public class MakeChatRooms {
    @NotNull(message = "파티 정보는 필수입니다.")
    @Schema(description = "받을 파티 정보 DTO")
    private PartyDto partyDto;
}
