package board.boardTest.controller;

import board.boardTest.domain.Comment;
import board.boardTest.domain.commentdtos.CommentDto;
import board.boardTest.domain.commentdtos.CommentToCommentDto;
import board.boardTest.domain.commentdtos.CommentViewDto;
import board.boardTest.service.BoardService;
import board.boardTest.service.CommentService;
import board.boardTest.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
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

    @ResponseBody
    @PostMapping("/write-comment-to")
    public Map<String, Object> writeCommentTo(@RequestBody CommentDto commentDto) {
        commentService.saveCommentTo(commentDto);

        Map<String, Object> response = new HashMap<>();

        return response;
    }

    @GetMapping("/get-comment-to")
    public ResponseEntity<List<CommentDto>> getCommentToComment(@RequestParam(name = "boardId") Long boardId,
                                                                @RequestParam(name = "index") Integer index) {
        CommentToCommentDto commentToCommentDto = new CommentToCommentDto();
        commentToCommentDto.setIndex(index);
        commentToCommentDto.setBoardId(boardId);
        List<CommentDto> returnList = commentService.getCommentToComments(commentToCommentDto);
        return new ResponseEntity<>(returnList, HttpStatus.OK);
    }

    private CommentViewDto getCommentViewDto(CommentDto commentDto, CommentDto savedCommentDto) {
        CommentViewDto commentViewDto = new CommentViewDto();
        commentViewDto.setCommentContent(savedCommentDto.getCommentContent());
        commentViewDto.setMemberName(commentDto.getMember().getName());
        return commentViewDto;
    }
}
