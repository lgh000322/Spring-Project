package UniP_server_chat.Unip_party_chat.global.exception.custom;

import UniP_server_chat.Unip_party_chat.global.exception.errorCode.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends RuntimeException {
    private ErrorCode errorCode;
}
