package board.boardTest.service;

import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import board.boardTest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
            throw new RuntimeException("중복된 이름 또는 아이디를 입력하셨습니다.");
        }
        savedMember.setPw(passwordEncoder.encode(savedMember.getPw()));
        Optional<MemberDto> savedMemberDto = memberRepository.save(savedMember);
        MemberDto memberDto = savedMemberDto.get();
        return memberDto;
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
        Optional<MemberDto> result = memberRepository.findBySequence(sequence);

        if (result.isPresent()) {
            return result.get();
        }

        return null;

    }

    /**
     * 회원의 아이디로 찾기
     * @param id 회원의 아이디
     * @return 비밀번호를 제외한 회원의 dto
     */

    public MemberDto findMemberById(String id) {
        return memberRepository.findById(id).get();
    }

    /**
     * 로그인을 하기 위해 id와 pw만 가지는 dto를 반환
     * @param id 회원의 아이디
     * @return 로그인 dto
     */
    public LoginMemberDto findMemberByIdLoginMemberDto(String id) {
        return memberRepository.findByIdLoginDto(id).get();
    }


    /**
     * 회원의 아이디나 이름이 중복된 값이 있는지 확인
     * @param id 회원의 아이디
     * @param name 회원의 이름
     * @return 회원의 아이디와 이름이 테이블에 저장되어 있지 않으면 true 반환
     */
    public boolean duplicateIdAndName(String id, String name) {
        Optional<MemberDto> findMemberById = memberRepository.findById(id);
        Optional<MemberDto> findMemberByName = memberRepository.findByName(name);

        return findMemberById.isEmpty() && findMemberByName.isEmpty();
    }
}
