package UniP_server_chat.Unip_party_chat.domain.member.service;

import UniP_server_chat.Unip_party_chat.domain.member.dto.MemberDto;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.repository.MemberRepository;
import UniP_server_chat.Unip_party_chat.domain.member.repository.MemberRepositoryCustom;
import UniP_server_chat.Unip_party_chat.global.exception.custom.CustomException;
import UniP_server_chat.Unip_party_chat.global.exception.errorCode.MemberErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CustomMemberService {

    private final MemberRepository memberRepository;

    public Member loadUserByUsername(String username) {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(MemberErrorCode.MEMBER_NOT_FOUND));

        return member;
    }

}
