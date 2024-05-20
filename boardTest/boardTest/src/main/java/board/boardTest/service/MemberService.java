package board.boardTest.service;

import board.boardTest.domain.Member;
import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import board.boardTest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**회원 저장 (아이디와 이름 중복검사 실행)
     * @param savedMember: 저장할 회원의 dto
     */
    @Transactional
    public MemberDto save(SavedMember savedMember) {
        if (!duplicateIdAndName(savedMember.getId(), savedMember.getName())) {
            return null;
        }
        savedMember.setPw(passwordEncoder.encode(savedMember.getPw()));

        Member save = memberRepository.save(savedMember);

        return Member.memberToMemberDto(save);
    }


    /**
     * 회원 전체 삭제
     */
    @Transactional
    public void deleteAll() {
        Integer deleteAllResult = memberRepository.deleteAll();
        log.info("회원 전체 삭제 로직 실행: {}", deleteAllResult);
    }


    /**
     * 회원의 순서번호로 특정 회원 삭제
     * @param sequence 회원의 기본키(순서번호)
     */
    @Transactional
    public Integer deleteBySequence(Long sequence) {
        Integer deleteByIdResult = memberRepository.deleteBySequence(sequence);
        log.info("특정 회원(순서번호) 삭제 로직 실행: {}", sequence);

        return deleteByIdResult;
    }


    /**
     * 회원 sequence로 찾기
     * @param sequence 회원의 기본키(순서번호)
     * @return 비밀번호를 제외한 회원의 dto
     */

    public MemberDto findMemberBySequence(Long sequence) {
        Optional<Member> result = memberRepository.findBySequence(sequence);

        if (result.isEmpty()) {
            return null;
        }

        return Member.memberToMemberDto(result.get());
    }

    /**
     * 회원의 아이디로 찾기
     * @param id 회원의 아이디
     * @return 비밀번호를 제외한 회원의 dto
     */

    public MemberDto findMemberById(String id) {
        Optional<Member> findMember = memberRepository.findById(id);

        if (findMember.isEmpty()) {
            return null;
        }

        return Member.memberToMemberDto(findMember.get());
    }

    /**
     * 로그인을 하기 위해 id와 pw만 가지는 dto를 반환
     * @param id 회원의 아이디
     * @return 로그인 dto
     */
    public LoginMemberDto findMemberByIdLoginMemberDto(String id) {
        Optional<Member> findMember = memberRepository.findByIdLoginDto(id);

        if (findMember.isEmpty()) {
            return null;
        }

        return Member.memberToLoginMemberDto(findMember.get());
    }

    public MemberDto findMemberByName(String name) {
        Optional<Member> findMember = memberRepository.findByName(name);

        if (findMember.isEmpty()) {
            return null;
        }

        return Member.memberToMemberDto(findMember.get());
    }


    /**
     * 회원의 아이디나 이름이 중복된 값이 있는지 확인
     * @param id 회원의 아이디
     * @param name 회원의 이름
     * @return 회원의 아이디와 이름이 테이블에 저장되어 있지 않으면 true 반환
     */
    public boolean duplicateIdAndName(String id, String name) {
        Optional<Member> findMemberById = memberRepository.findById(id);
        Optional<Member> findMemberByName = memberRepository.findByName(name);

        return findMemberById.isEmpty() && findMemberByName.isEmpty();
    }

    public String getSecurityId() {
        SecurityContext context = SecurityContextHolder.getContextHolderStrategy().getContext();
        Authentication authentication = context.getAuthentication();
        String id = authentication.getName();
        return id;
    }
}
