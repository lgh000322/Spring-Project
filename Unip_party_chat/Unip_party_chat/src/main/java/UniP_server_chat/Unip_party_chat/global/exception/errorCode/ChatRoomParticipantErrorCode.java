package UniP_server_chat.Unip_party_chat.global.exception.errorCode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ChatRoomParticipantErrorCode implements ErrorCode {
    //Todo: 추가해야 됨
    CANT_DELETE(HttpStatus.BAD_REQUEST,"해당 채팅방은 제거할 수 없습니다.");

    private HttpStatus httpStatus;
    private String message;
}
