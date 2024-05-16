package board.boardTest.controller;

import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import board.boardTest.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * todo
     * 1. 로그인 실패 핸들러를 통해 login으로 다시 돌아오면 bindingResult를 사용해서 "아이디 또는 비밀번호를 잘못 입력하셨습니다" 를 출력하게 해야함
     * 2. 로그인 성공시 게시판 페이지로 가게끔 구현
     * 3. 각 게시판 기능 구현
     * 4. 각 게시판 마다 댓글 기능 구현
     * 5. 각 댓글마다 대댓글 기능 구현
     */
    @GetMapping("/login")
    public String login(@ModelAttribute(name = "LoginMemberDto") LoginMemberDto loginMemberDto) {
        return "login";
    }


    @GetMapping("/join")
    public String join(@ModelAttribute(name = "SavedMember") SavedMember savedMember) {
        return "join";
    }

    @PostMapping("/join")
    public String joinProcess(@Valid @ModelAttribute(name = "SavedMember") SavedMember savedMember,
                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.info("회원가입버튼의 바인딩 오류");
            return "join";
        }

        memberService.save(savedMember);
        log.info("회원가입 완료");

        return "redirect:/login";
    }
}
