package UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.controller;

import UniP_server_chat.Unip_party_chat.domain.chatRoom.entity.ChatRoom;
import UniP_server_chat.Unip_party_chat.domain.chatRoom.service.ChatRoomService;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto.ChatRoomRequestDto;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.dto.RemoveParticipant;
import UniP_server_chat.Unip_party_chat.domain.chatRoomParticipant.service.ChatRoomParticipantService;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.service.CustomMemberService;
import UniP_server_chat.Unip_party_chat.domain.party.dto.PartyDto;
import UniP_server_chat.Unip_party_chat.global.baseResponse.ResponseDto;
import UniP_server_chat.Unip_party_chat.global.memberinfo.MemberInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@Tag(name = "채팅 참여자", description = "채팅 참여자 관련 API")
public class ChatRoomParticipantController {
    private final ChatRoomParticipantService chatRoomParticipantService;
    private final CustomMemberService customMemberService;
    private final ChatRoomService chatRoomService;
    private final MemberInfo memberInfo;

    /**
     * 채팅방에 사용자 초대
     *
     * @param chatRoomRequestDto
     * @return
     */
    @PostMapping("/chat/room/participant")
    @Operation(summary = "채팅방 초대", description = "채팅방에 사용자들을 초대한다.")
    public ResponseEntity<ResponseDto<?>> addParticipantSpecificChatRoom(@RequestBody @Valid ChatRoomRequestDto chatRoomRequestDto) {
        List<Member> members = chatRoomRequestDto.getUsername().stream()
                .map(username -> customMemberService.loadUserByUsername(username))
                .collect(Collectors.toList());

        ChatRoom chatRoom = chatRoomService.findById(chatRoomRequestDto.getRoomId());
        chatRoomParticipantService.makeChatRoomParticipants(members, chatRoom, false);
        return ResponseEntity.ok().body(ResponseDto.of("회원 추가 성공", null));
    }

    /**
     * 채팅방 나가기
     */
    @DeleteMapping("/chat/room")
    @Operation(summary = "채팅방 나가기", description = "채팅방에서 나간다.")
    public ResponseEntity<ResponseDto<?>> deleteParticipantSpecificChatRoom(@RequestBody @Valid RemoveParticipant removeParticipant) {
        Member member = memberInfo.getThreadLocalMember();
        chatRoomParticipantService.deleteChatRoomParticipant(member, removeParticipant.roomId());
        return ResponseEntity.ok().body(ResponseDto.of("채팅방을 정상적으로 나갔습니다.", null));
    }

    /**
     * 파티 아이디 기반 채팅방 입장
     */
    @PostMapping("/chat/room/{partyId}")
    @Operation(summary = "파티참가와 동시에 채팅방 입장", description = "채팅방에 입장한다.")
    public ResponseEntity<ResponseDto<?>> enterPartyChat(@PathVariable(name = "partyId") Long partyId) {
        chatRoomService.participateInPartyChat(partyId, memberInfo.getThreadLocalMember());
        return ResponseEntity.ok().body(ResponseDto.of("파티 채팅에 입장했습니다.", null));
    }
}
