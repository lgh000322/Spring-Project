package UniP_server_chat.Unip_party_chat.domain.party.entity;

import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.PartyErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private int partyLimit;

    private int peopleCount;

    private LocalDateTime startTime; // LocalDateTime 사용

    private LocalDateTime endTime; // 변수명 소문자로 수정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Version  // 낙관적 락을 위한 버전 필드 추가
    private int version;

    public boolean isPartyFull() {
        return peopleCount >= partyLimit;
    }

    public void joinParty() {
        if (isPartyFull()) {
            throw new CustomException(PartyErrorCode.PARTY_FULL);
        }
        peopleCount++;
    }

    public void leaveParty() {
        if (peopleCount <= 0) {
            throw new CustomException(PartyErrorCode.NO_MEMBER_TO_LEAVE);
        }
        peopleCount--;
    }


}
