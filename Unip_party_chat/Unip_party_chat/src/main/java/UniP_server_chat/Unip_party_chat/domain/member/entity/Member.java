package UniP_server_chat.Unip_party_chat.domain.member.entity;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Enum.Role;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Enum.Status;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String profile_image;

    private boolean auth;

    private int point;

    @Enumerated(EnumType.STRING)
    private Status status;

    public void plusPoint(int point){
        this.point += point;
    }
}