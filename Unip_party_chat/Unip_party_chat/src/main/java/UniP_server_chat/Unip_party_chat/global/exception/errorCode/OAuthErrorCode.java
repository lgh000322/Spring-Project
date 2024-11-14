package UniP_server_chat.Unip_party_chat.global.exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum OAuthErrorCode implements ErrorCode {
    ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Access Token이 만료되었습니다."),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "Access Token을 찾을 수 없습니다."),
    REFRESH_TOKEN_NULL(HttpStatus.UNAUTHORIZED, "Refresh Token을 찾을 수 없습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "Refresh Token을 찾을 수 없습니다."),
    REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Refresh Token이 만료되었습니다."),
    TOKEN_VALID_FAIL(HttpStatus.UNAUTHORIZED, "토큰이 잘못되었습니다."),
    LOGIN_TYPE_NOT_FOUND(HttpStatus.UNAUTHORIZED, "잘못된 로그인 타입");
    private HttpStatus httpStatus;
    private String message;
}
