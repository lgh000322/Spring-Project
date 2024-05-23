package board.boardTest.repository;

import board.boardTest.domain.Board;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    //모든 튜플을 찾는 메소드
    public Optional<Page<Board>> findAll(Pageable pageable) {
        List<Board> findAll = em.createQuery("select b from Board b" +
                        " order by b.id desc", Board.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (findAll.isEmpty()) {
            return Optional.empty();
        }

        long total = em.createQuery("select count(b) from Board b", Long.class)
                .getSingleResult();

        return Optional.of(new PageImpl<>(findAll, pageable, total));
    }

    public Board save(WriteBoardDto writeBoardDto) {
        Board board = Board.writeBoardDtoToBoard(writeBoardDto);
        em.persist(board);
        return board;
    }

    public Optional<Board> findById(Long id) {
        try {
            Board findBoard = em.createQuery("select b from Board b" +
                            " where b.id = :id", Board.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return Optional.of(findBoard);

        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    public void deleteById(Long id) {
        Board board = em.find(Board.class, id);

        if (board != null) {
            em.remove(board);
        }
    }


    public Optional<Page<Board>> findByTitleContaining(String keyword, Pageable pageable) {
        List<Board> findAll = em.createQuery("select b from Board b" +
                        " where b.title like :keyword" +
                        " order by b.id desc", Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (findAll.isEmpty()) {
            return Optional.empty();
        }

        long total = em.createQuery("select count(b) from Board b" +
                        " where b.title like :keyword", Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();

        return Optional.of(new PageImpl<>(findAll, pageable, total));

    }

    public Optional<Page<Board>> findByMember_Name(String keyword, Pageable pageable) {
        List<Board> findAll = em.createQuery("select b from Board b" +
                        " join fetch b.member m" +
                        " where m.name like :keyword" +
                        " order by b.id desc", Board.class)
                .setParameter("keyword", "%" + keyword + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        if (findAll.isEmpty()) {
            return Optional.empty();
        }

        long total = em.createQuery("select count(b) from Board b" +
                        " where b.member.name like :keyword", Long.class)
                .setParameter("keyword", "%" + keyword + "%")
                .getSingleResult();

        return Optional.of(new PageImpl<>(findAll, pageable, total));
    }
}
