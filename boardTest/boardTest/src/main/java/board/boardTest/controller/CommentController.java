package board.boardTest.controller;

import board.boardTest.domain.Comment;
import board.boardTest.domain.commentdtos.CommentDto;
import board.boardTest.domain.commentdtos.CommentToCommentDto;
import board.boardTest.domain.commentdtos.CommentViewDto;
import board.boardTest.domain.commentdtos.ViewCommentToCommentDto;
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

    @PostMapping("/write-comment-to")
    public ResponseEntity<ViewCommentToCommentDto> writeCommentTo(@RequestBody CommentDto commentDto) {
        CommentDto savedCommentDto = commentService.saveCommentTo(commentDto);
        ViewCommentToCommentDto viewCommentToCommentDto = new ViewCommentToCommentDto();
        viewCommentToCommentDto.setMemberName(savedCommentDto.getMember().getName());
        viewCommentToCommentDto.setCommentContent(savedCommentDto.getCommentContent());

        return new ResponseEntity<>(viewCommentToCommentDto, HttpStatus.OK);
    }

    @GetMapping("/get-comment-to")
    public ResponseEntity<List<ViewCommentToCommentDto>> getCommentToComment(@RequestParam(name = "boardId") Long boardId,
                                                                             @RequestParam(name = "index") Integer index) {
        CommentToCommentDto commentToCommentDto = new CommentToCommentDto();
        commentToCommentDto.setIndex(index);
        commentToCommentDto.setBoardId(boardId);
        List<CommentDto> result = commentService.getCommentToComments(commentToCommentDto);
        List<ViewCommentToCommentDto> list = new ArrayList<>();
        result.forEach(commentDto -> {
            ViewCommentToCommentDto viewCommentToCommentDto = new ViewCommentToCommentDto();
            viewCommentToCommentDto.setCommentContent(commentDto.getCommentContent());
            viewCommentToCommentDto.setMemberName(commentDto.getMember().getName());
            list.add(viewCommentToCommentDto);
        });

        return new ResponseEntity(list, HttpStatus.OK);

    }

    private CommentViewDto getCommentViewDto(CommentDto commentDto, CommentDto savedCommentDto) {
        CommentViewDto commentViewDto = new CommentViewDto();
        commentViewDto.setCommentContent(savedCommentDto.getCommentContent());
        commentViewDto.setMemberName(commentDto.getMember().getName());
        return commentViewDto;
    }
}
