package board.boardTest.controller;

import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import board.boardTest.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;


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

        MemberDto save = memberService.save(savedMember);

        if (save == null) {
            return "join";
        }

        log.info("회원가입 완료");

        return "redirect:/login";
    }
}
