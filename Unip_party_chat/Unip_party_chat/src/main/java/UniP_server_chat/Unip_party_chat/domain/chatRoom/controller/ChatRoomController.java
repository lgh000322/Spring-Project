package UniP_server_chat.Unip_party_chat.domain.chatRoom.controller;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.dto.MakeChatRooms;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.service.ChatRoomService;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@Tag(name = "채팅방", description = "채팅방 관련 API")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    private final MemberInfo memberInfo;
    /**
     * 전체 채팅방을 조회
     * @return jwt 회원의 전체채팅방 조회 응답
     */
    @GetMapping("/chat/rooms")
    @Operation(summary = "전체 채팅방 조회", description = "특정 회원의 전체 채팅방을 가져온다.")
    public ResponseEntity<ResponseDto<?>> getChatRooms() {
        return ResponseEntity.ok().body(ResponseDto.of("전체 채팅방 조회 성공", chatRoomService.getChatRooms()));
    }

    /**
     * 초기 채팅방 생성
     * @param makeChatRooms 요청 데이터
     * @return jwt 회원의 채팅방 생성응답
     */
    @PostMapping("/chat/rooms")
    @Operation(summary = "채팅방 생성", description = "초기 채팅방을 생성한다.")
    public ResponseEntity<ResponseDto<?>> makeChatRoom(@RequestBody @Valid MakeChatRooms makeChatRooms) {
        chatRoomService.makeChatRoomInit(makeChatRooms, memberInfo.getThreadLocalMember());
        return ResponseEntity.ok().body(ResponseDto.of("채팅방 생성 성공", null));
    }

}
