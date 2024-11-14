package UniP_server_chat.Unip_party_chat.global.memberinfo;


import UniP_server_chat.Unip_party_chat.domain.member.entity.Member;
import org.springframework.stereotype.Component;

@Component
public class MemberInfo {

    private final ThreadLocal<Member> threadLocalMember = new ThreadLocal<>();

    public Member getThreadLocalMember() {
        return threadLocalMember.get();
    }

    public void setThreadLocalMember(Member member) {
        threadLocalMember.set(member);
    }

    public void clear() {
        threadLocalMember.remove();
    }

}
