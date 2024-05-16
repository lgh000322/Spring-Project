package board.boardTest.service;

import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @AfterEach
    void afterEach() {
        memberService.deleteAll();
    }

    @Test
    void save() {
        //given
        SavedMember savedMember = new SavedMember();
        savedMember.setId("1111");
        savedMember.setPw("1111");
        savedMember.setName("1111");

        //when
        MemberDto savedMemberDto = memberService.save(savedMember);
        MemberDto findMemberDto = memberService.findMemberBySequence(savedMemberDto.getSequence());

        //then
        assertThat(savedMemberDto.getSequence()).isEqualTo(findMemberDto.getSequence());
    }

    @Test
    void deleteBySequence() {
        //given
        Integer deletedAmount=0;
        SavedMember savedMember1 = new SavedMember();
        savedMember1.setId("1111");
        savedMember1.setPw("1111");
        savedMember1.setName("1111");

        SavedMember savedMember2 = new SavedMember();
        savedMember2.setId("2222");
        savedMember2.setPw("2222");
        savedMember2.setName("2222");

        SavedMember savedMember3 = new SavedMember();
        savedMember3.setId("3333");
        savedMember3.setPw("3333");
        savedMember3.setName("3333");

        memberService.save(savedMember1);
        memberService.save(savedMember2);
        memberService.save(savedMember3);

        //when
        deletedAmount += memberService.deleteBySequence(2L);
        MemberDto findMemberDto = memberService.findMemberBySequence(2L);

        //then
        assertThat(deletedAmount).isEqualTo(1);
        assertThat(findMemberDto).isNull();

    }

    @Test
    void findMemberById() {
        //given
        SavedMember savedMember1 = new SavedMember();
        savedMember1.setId("1111");
        savedMember1.setPw("1111");
        savedMember1.setName("1111");

        SavedMember savedMember2 = new SavedMember();
        savedMember2.setId("2222");
        savedMember2.setPw("2222");
        savedMember2.setName("2222");

        memberService.save(savedMember1);
        memberService.save(savedMember2);

        //when
        MemberDto findMemberDto = memberService.findMemberById("1111");

        //then
        assertThat(findMemberDto.getName()).isEqualTo(savedMember1.getName());
    }

    @Test
    void findMemberByIdLoginMemberDto() {
        //given
        SavedMember savedMember1 = new SavedMember();
        savedMember1.setId("1111");
        savedMember1.setPw("1111");
        savedMember1.setName("1111");

        SavedMember savedMember2 = new SavedMember();
        savedMember2.setId("2222");
        savedMember2.setPw("2222");
        savedMember2.setName("2222");

        memberService.save(savedMember1);
        memberService.save(savedMember2);

        //when
        LoginMemberDto findLoginMemberDto = memberService.findMemberByIdLoginMemberDto("1111");

        //then
        assertThat(findLoginMemberDto.getId()).isEqualTo(savedMember1.getId());

    }

    @Test
    void duplicateIdAndName() {
        //given
        boolean flag=false;
        SavedMember savedMember1 = new SavedMember();
        savedMember1.setId("1111");
        savedMember1.setPw("1111");
        savedMember1.setName("1111");

        memberService.save(savedMember1);

        //when
        boolean getFlag = memberService.duplicateIdAndName(savedMember1.getId(), savedMember1.getName());

        //then
        assertThat(getFlag).isEqualTo(flag);
    }
}