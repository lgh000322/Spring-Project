package board.boardTest.domain.memberdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class LoginMemberDto {


    @NotBlank(message = "아이디를 입력해주세요.")
    @Length(min = 8,max = 16,message = "최소 8자 최대 16자 입니다.")
    private String id;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Length(min = 8,max = 16,message = "최소 8자 최대 16자 입니다.")
    private String pw;
}
