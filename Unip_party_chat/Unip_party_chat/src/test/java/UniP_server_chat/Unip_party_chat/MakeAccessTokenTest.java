/*
package UniP_server_chat.Unip_party_chat;

import UniP_server_chat.Unip_party_chat.domain.chatLog.controller.ChatLogController;
import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import UniP_server_chat.Unip_party_chat.domain.member.repository.MemberRepository;
import UniP_server_chat.Unip_party_chat.global.filter.JwtUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
public class MakeAccessTokenTest {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void getAccessToken() throws Exception{
        Member member = memberRepository.findById(3L).orElseThrow();
        Assertions.assertThat(true).isTrue();
    }
}
*/
