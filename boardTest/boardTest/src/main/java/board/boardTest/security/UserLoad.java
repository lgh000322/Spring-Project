package board.boardTest.security;

import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserLoad implements UserDetailsService {

    private final MemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        /**
         * 특정 회원을 찾고 유저를 빌드함
         */
        LoginMemberDto findLoginMemberDto = memberService.findMemberByIdLoginMemberDto(id);

        if (findLoginMemberDto == null) {
            throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
        }

        return User.builder()
                .username(findLoginMemberDto.getId())
                .password(findLoginMemberDto.getPw())
                .build();
    }
}

