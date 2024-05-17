package board.boardTest.converter;

import board.boardTest.domain.Member;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.repository.MemberRepository;
import board.boardTest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StringToMemberConverter implements Converter<String, Member> {

    private final MemberRepository memberRepository;

    //source=> 회원의 이름
    @Override
    public Member convert(String source) {
        Optional<Member> findMember = memberRepository.findByName(source);

        if (findMember.isEmpty()) {
            throw new RuntimeException("해당 유저가 없습니다.");
        }

        return findMember.get();
    }
}
