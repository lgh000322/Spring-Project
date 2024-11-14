package UniP_server_chat.Unip_party_chat.domain.party.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

@Builder
@Schema(description = "파티정보 DTO")
public record PartyDto(
        @NotEmpty(message = "제목은 필수입니다.")
        @Schema(description = "파티의 제목", example = "두정동 00 갈사람 구함~")
        String title,

        @NotEmpty(message = "파티번호는 필수입니다.")
        @Schema(description = "파티의 번호", example = "10L")
        Long partyId
) {
}
