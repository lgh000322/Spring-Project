package board.boardTest.domain.boarddtos;

import board.boardTest.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriteBoardDto {
    private String title;

    private String content;

    private Member member;
}
