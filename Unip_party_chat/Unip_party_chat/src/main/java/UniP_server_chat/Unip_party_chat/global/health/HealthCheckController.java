package UniP_server_chat.Unip_party_chat.global.health;

import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<ResponseDto<?>> healthCheck() {
        return ResponseEntity.ok().body(ResponseDto.of("헬스 체크 성공", null));
    }
}
