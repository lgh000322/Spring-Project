package UniP_server_chat.Unip_party_chat.global.exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum PartyErrorCode implements ErrorCode {
    UNAUTHORIZED_ACCESS(HttpStatus.BAD_REQUEST, "제거 권한이 없습니다."),
    PARTY_NOT_FOUND(HttpStatus.NOT_FOUND, "파티를 찾을 수 없습니다."),
    ALREADY_JOINED(HttpStatus.BAD_REQUEST, "이미 파티에 가입되어있습니다."),
    MEMBER_NOT_IN_PARTY(HttpStatus.BAD_REQUEST, "맴버가 해당 파티에 존재하지 않습니다."),
    PARTY_FULL(HttpStatus.BAD_REQUEST, "이미 파티가 꽉찼습니다."),
    NO_MEMBER_TO_LEAVE(HttpStatus.BAD_REQUEST, "파티 인원수에 오류 발생" );
    private HttpStatus httpStatus;
    private String message;
}