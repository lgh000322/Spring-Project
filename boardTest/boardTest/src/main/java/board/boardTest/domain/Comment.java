package board.boardTest.domain;

import board.boardTest.domain.commentdtos.CommentDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Column(name = "comment_index")
    private Integer index;

    @Column(name = "comment_depth")
    private Integer depth;

    @Column(name = "comment_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_sequence")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;



    //========================== 연관관계 편의 메소드 ======================================


    protected Comment() {
    }

    private Comment(Long id, Integer index, String content, Member member, Board board, Integer depth) {
        this.depth = depth;
        this.index = index;
        this.id = id;
        this.content = content;
        this.member = member;
        this.board = board;
    }

    public static Comment commentDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment(commentDto.getCommentId(), commentDto.getIndex(), commentDto.getCommentContent(), commentDto.getMember(), commentDto.getBoard(), commentDto.getDepth());
        return comment;
    }

    public static CommentDto commentToCommentDto(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setCommentId(comment.getId());
        commentDto.setIndex(comment.getIndex());
        commentDto.setMember(comment.getMember());
        commentDto.setBoard(comment.getBoard());
        commentDto.setDepth(comment.getDepth());
        commentDto.setCommentContent(comment.getContent());
        return commentDto;
    }
}
