package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Schema(description = "채팅방 초대 DTO")
public class ChatRoomRequestDto {

    @NotEmpty(message = "유저 이름은 필수입니다.")
    @Schema(description = "초대할 회원의 username",example = "google ....")
    private List<String> username;

    @NotEmpty(message = "방 번호는 필수입니다.")
    @Schema(description = "방 번호", example = "aefklbewkjlab12-dkljsabfi2-dkslfsdfa")
    private UUID roomId;
}
