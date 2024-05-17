package board.boardTest.controller;

import board.boardTest.domain.Member;
import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.service.BoardService;
import board.boardTest.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping
    public String boardView(Model model) {
        List<BoardDto> findBoards = boardService.findAll();
        model.addAttribute("BoardDto", findBoards);
        return "boards";
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable(name = "boardId") Long boardId, Model model) {
        WriteBoardDto writeBoardDto = boardService.findById(boardId);
        model.addAttribute("WriteBoardDto", writeBoardDto);
        return "boardView";
    }

    @GetMapping("/write")
    public String writeBoard(@ModelAttribute(name = "WriteBoardDto") WriteBoardDto writeBoardDto) {
        String id = memberService.getSecurityId();
        MemberDto findMemberDto = memberService.findMemberById(id);

        writeBoardDto.setMember(Member.memberDtoToMember(findMemberDto));

        return "board-write";
    }

    @PostMapping("/write")
    public String writeBoardProcess(@ModelAttribute(name = "WriteBoardDto") WriteBoardDto writeBoardDto,
                                    RedirectAttributes redirectAttributes) {
        BoardDto boardDto = boardService.writeBoard(writeBoardDto);
        redirectAttributes.addAttribute("id", boardDto.getId());
        return "redirect:/board/{id}";
    }
}
