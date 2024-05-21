package board.boardTest.controller;

import board.boardTest.domain.Board;
import board.boardTest.domain.Member;
import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.domain.commentdtos.CommentDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.service.BoardService;
import board.boardTest.service.CommentService;
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
    private final CommentService commentService;

    @GetMapping
    public String boardView(Model model) {
        List<BoardDto> findBoards = boardService.findAll();
        model.addAttribute("BoardDto", findBoards);
        return "boards";
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable(name = "boardId") Long boardId, Model model) {
        WriteBoardDto writeBoardDto = boardService.findById(boardId);
        List<CommentDto> findCommentDtos = commentService.getComments(writeBoardDto);
        writeBoardDto.setBoardId(boardId);

        String memberId = memberService.getSecurityId();
        MemberDto findMemberDto = memberService.findMemberById(memberId);

        //조회수 증가
        boardService.updateView(writeBoardDto.getBoardId());

        //대댓글을 가지고 있는지 확인

        model.addAttribute("CommentDto", findCommentDtos);
        model.addAttribute("WriteBoardDto", writeBoardDto);
        model.addAttribute("LoginMemberName", findMemberDto.getName());
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

    @PostMapping("/delete/{boardId}")
    public String deleteBoardById(@PathVariable(name = "boardId") Long boardId) {
        boardService.deleteById(boardId);
        return "redirect:/board";
    }
}
