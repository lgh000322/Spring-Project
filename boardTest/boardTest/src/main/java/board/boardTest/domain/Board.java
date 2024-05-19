package board.boardTest.domain;

import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

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
        Board board = new Board(writeBoardDto.getContent(), writeBoardDto.getTitle(), writeBoardDto.getMember(), writeBoardDto.getView());
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
        writeBoardDto.setBoardId(board.getId());
        writeBoardDto.setMember(board.getMember());
        writeBoardDto.setContent(board.getContent());
        writeBoardDto.setTitle(board.getTitle());
        writeBoardDto.setView(board.getView());

        return writeBoardDto;
    }

    //============================== 비즈니스 로직 ===========================
    public void addViews() {
        this.view +=1;
    }
}
