package board.boardTest.domain.attachedfiledto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class ViewFileDto {
    private List<UploadFile> imageFileNames;

    private List<UploadFile> textFileNames;

}
