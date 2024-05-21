package board.boardTest.domain.commentdtos;

import board.boardTest.domain.Board;
import board.boardTest.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private Long commentId;

    private Integer index;

    private Long boardId;

    private Board board;

    private String memberName;

    private Member member;

    private String commentContent;

    private Integer depth;

    private Boolean hasChild;
}
