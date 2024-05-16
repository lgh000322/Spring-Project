package board.boardTest.domain.memberdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginMemberDto {


    @NotNull
    @NotBlank
    @Length(min = 8,max = 16,message = "최소 8자 최대 16자 입니다.")
    private String id;

    @NotNull
    @NotBlank
    @Length(min = 8,max = 16,message = "최소 8자 최대 16자 입니다.")
    private String pw;
}
