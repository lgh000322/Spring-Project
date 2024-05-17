package board.boardTest.domain.boarddtos;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardDto {
    private Long id;

    private String content;

    private String title;

    private Integer view;
}
