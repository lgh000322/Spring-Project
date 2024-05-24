package board.boardTest.domain.attachedfiledto;

import board.boardTest.domain.Board;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachedFileDto {
    private Board board;

    private String originalName;

    private String storedName;
}
