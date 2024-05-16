package board.boardTest.domain;

import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_sequence")
    private Long sequence;

    @Column(name = "member_name", unique = true)
    private String name;

    @Column(name = "member_id", unique = true)
    private String id;

    @Column(name = "member_pw")
    private String pw;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Board> boardList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<CommentToComment> commentToCommentList = new ArrayList<>();


    //========================== 연관관계 편의 메소드 ========================================//

    protected Member() {
    }

    private Member(String name, String id, String pw) {
        this.name = name;
        this.id = id;
        this.pw = pw;
    }

    public static Member savedMemberToMember(SavedMember savedMember) {
        return new Member(savedMember.getName(), savedMember.getId(), savedMember.getPw());
    }

    public static MemberDto memberToMemberDto(Member member) {
        MemberDto memberDto = new MemberDto();
        memberDto.setId(member.getId());
        memberDto.setSequence(member.getSequence());
        memberDto.setName(member.getName());

        return memberDto;
    }

    public static LoginMemberDto memberToLoginMemberDto(Member member) {
        LoginMemberDto loginMemberDto = new LoginMemberDto();
        loginMemberDto.setId(member.getId());
        loginMemberDto.setPw(member.getPw());
        return loginMemberDto;
    }
}
