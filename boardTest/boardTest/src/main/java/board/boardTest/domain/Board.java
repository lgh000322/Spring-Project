package board.boardTest.domain;

import board.boardTest.domain.boarddtos.BoardDto;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_content")
    private String content;

    @Column(name = "board_title")
    private String title;

    @Column(name = "board_view")
    private Integer view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_sequence")
    private Member member;

    //=============================== 연관관계 편의 메소드 ===========================/

    protected Board() {
    }

    private Board(Long id, String content, String title, Integer view) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.view = view;
    }

    public static BoardDto dtoToBoard(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setContent(board.getContent());
        boardDto.setTitle(board.getTitle());
        boardDto.setView(board.getView());

        return boardDto;
    }
}
