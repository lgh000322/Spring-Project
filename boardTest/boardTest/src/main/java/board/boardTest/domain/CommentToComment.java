package board.boardTest.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class CommentToComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_to_comment_id")
    private Long id;

    @Column(name = "comment_to_comment_index")
    private Integer index;

    @Column(name = "comment_to_comment_content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_sequence")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;
}
