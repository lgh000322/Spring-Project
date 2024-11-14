package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "제거할 채팅 참여자 DTO")
public record RemoveParticipant(@NotNull(message = "방 번호는 필수입니다.")
                                @Schema(description = "방 번호", example = "10") UUID roomId) {

}
