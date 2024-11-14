package UniP_server_chat.Unip_party_chat.domain.chatStore.entity;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.context.annotation.Lazy;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class ChatStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
}
