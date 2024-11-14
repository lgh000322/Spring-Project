package UniP_server_chat.Unip_party_chat.global.exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ChatLogErrorCode implements ErrorCode {
    //TODO: 추가해야됨
    CHAT_LOG_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅 로그를 찾을 수 없습니다."),
    CHAT_LOG_MAPPING(HttpStatus.BAD_REQUEST, "채팅 메시지큐 변환과정에서 실패했습니다."),
    EX(HttpStatus.BAD_REQUEST, "예시 텍스트");

    private HttpStatus httpStatus;
    private String message;
}
