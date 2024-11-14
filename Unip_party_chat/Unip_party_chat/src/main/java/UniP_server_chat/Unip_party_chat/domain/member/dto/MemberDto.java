package UniP_server_chat.Unip_party_chat.domain.member.dto;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String role;
    private String username;
    private String name;
    private boolean auth;
    private String profile_image;
    private Status status;
}
