package board.boardTest.domain.commentdtos;

import board.boardTest.domain.Board;
import board.boardTest.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long boardId;

    private Board board;

    private String memberName;

    private Member member;

    private String commentContent;
}
