package board.boardTest.domain.boarddtos;

import board.boardTest.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private Long id;

    private String title;

    private Integer view;

    private Member member;
}
