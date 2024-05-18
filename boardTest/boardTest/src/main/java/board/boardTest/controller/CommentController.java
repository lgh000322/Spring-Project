package board.boardTest.controller;

import board.boardTest.domain.commentdtos.CommentDto;
import board.boardTest.domain.commentdtos.CommentViewDto;
import board.boardTest.service.BoardService;
import board.boardTest.service.CommentService;
import board.boardTest.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final BoardService boardService;
    @ResponseBody
    @PostMapping("/write")
    public Map<String,Object> writeProcess(@RequestBody CommentDto commentDto) {
        CommentDto savedCommentDto = commentService.save(commentDto);

        CommentViewDto commentViewDto = getCommentViewDto(commentDto, savedCommentDto);

        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("comment", commentViewDto);
        return response;
    }

    private CommentViewDto getCommentViewDto(CommentDto commentDto, CommentDto savedCommentDto) {
        CommentViewDto commentViewDto = new CommentViewDto();
        commentViewDto.setCommentContent(savedCommentDto.getCommentContent());
        commentViewDto.setMemberName(commentDto.getMember().getName());
        return commentViewDto;
    }
}
