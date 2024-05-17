package board.boardTest.domain;

import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
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

    private Board(String content, String title, Member member,Integer view) {
        this.content = content;
        this.title = title;
        this.member = member;
        this.view = view;
    }

    public static BoardDto dtoToBoard(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setView(board.getView());
        boardDto.setMember(board.getMember());
        return boardDto;
    }

    public static Board writeBoardDtoToBoard(WriteBoardDto writeBoardDto) {
        Board board = new Board(writeBoardDto.getContent(), writeBoardDto.getTitle(), writeBoardDto.getMember(), 0);
        return board;
    }

    public static BoardDto boardToBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setMember(board.getMember());
        boardDto.setTitle(board.getTitle());
        boardDto.setView(board.getView());
        return boardDto;
    }

    public static WriteBoardDto boardToWriteBoardDto(Board board) {
        WriteBoardDto writeBoardDto = new WriteBoardDto();
        writeBoardDto.setMember(board.getMember());
        writeBoardDto.setContent(board.getContent());
        writeBoardDto.setTitle(board.getTitle());

        return writeBoardDto;
    }
}
