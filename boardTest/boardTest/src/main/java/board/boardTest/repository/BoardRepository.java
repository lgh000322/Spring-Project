package board.boardTest.repository;

import board.boardTest.domain.Board;
import board.boardTest.domain.boarddtos.BoardDto;
import jakarta.persistence.EntityManager;
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
    public Optional<List<BoardDto>> findAll() {
        List<Board> findAll = em.createQuery("select b from Board b", Board.class)
                .getResultList();

        if (findAll.isEmpty()) {
            return Optional.empty();
        }

        List<BoardDto> returnValues = new ArrayList<>();

        for (Board board : findAll) {
            returnValues.add(Board.dtoToBoard(board));
        }

        return Optional.of(returnValues);
    }



}
