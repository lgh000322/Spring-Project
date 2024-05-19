package board.boardTest.repository;

import board.boardTest.domain.Board;
import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    //모든 튜플을 찾는 메소드
    public Optional<List<Board>> findAll() {
        List<Board> findAll = em.createQuery("select b from Board b", Board.class)
                .getResultList();

        if (findAll.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(findAll);
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



}
