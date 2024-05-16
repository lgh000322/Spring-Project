package board.boardTest.domain.memberdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class SavedMember {

    @NotNull
    @NotBlank
    @Length(min = 6,max = 12,message = "최소 6 최대 12자 입니다.")
    private String id;

    @NotNull
    @NotBlank
    @Length(min = 6,max = 12,message = "최소 6 최대 12자 입니다.")
    private String pw;

    @NotNull
    @NotBlank
    @Length(min = 6,max = 12,message = "최소 6 최대 12자 입니다.")
    private String name;
}
