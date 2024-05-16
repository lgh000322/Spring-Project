package board.boardTest.repository;

import board.boardTest.domain.Member;
import board.boardTest.domain.memberdtos.LoginMemberDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.domain.memberdtos.SavedMember;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    //저장
    public Optional<MemberDto> save(SavedMember savedMember) {
        Member member = Member.savedMemberToMember(savedMember);
        em.persist(member);

        Optional<MemberDto> memberDto = Optional.of(Member.memberToMemberDto(member));
        return memberDto;
    }

    //sequence로 회원찾기
    public Optional<MemberDto> findBySequence(Long sequence) {
        Member member = em.find(Member.class, sequence);
        if (member == null) {
            return Optional.empty();
        }
        return Optional.of(Member.memberToMemberDto(member));

    }

    //회원 id로 회원찾기(MemberDto 반환)
    public Optional<MemberDto> findById(String id) {
        try {
            Member findMember = em.createQuery(
                            "select m from Member m" +
                                    " where m.id = :id", Member.class
                    )
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.of(Member.memberToMemberDto(findMember));

        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    //회원 id로 회원찾기 (LoginMemberDto 반환)
    public Optional<LoginMemberDto> findByIdLoginDto(String id) {
        try {
            Member findMember = em.createQuery(
                            "select m from Member m" +
                                    " where m.id = :id", Member.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.of(Member.memberToLoginMemberDto(findMember));

        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    //회원의 이름으로 회원찾기
    public Optional<MemberDto> findByName(String name) {
        try {
            Member findMember = em.createQuery(
                            "select m from Member m" +
                                    " where m.name = :name", Member.class
                    )
                    .setParameter("name", name)
                    .getSingleResult();

            return Optional.of(Member.memberToMemberDto(findMember));
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    //해당 테이블의 모든 튜플 삭제하기
    public Integer deleteAll() {
        return em.createQuery("delete from Member m")
                .executeUpdate();
    }


    //sequence로 특정 튜플 삭제하기
    public Integer deleteBySequence(Long sequence) {
        return em.createQuery(
                        "delete from Member m" +
                                " where m.sequence = :sequence"
                )
                .setParameter("sequence", sequence)
                .executeUpdate();
    }


}
