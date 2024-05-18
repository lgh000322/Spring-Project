package board.boardTest.repository;

import board.boardTest.domain.Board;
import board.boardTest.domain.Comment;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.domain.commentdtos.CommentDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CommentRepository {

    private final EntityManager em;

    public Comment save(CommentDto commentDto) {
        Comment comment = Comment.commentDtoToComment(commentDto);
        em.persist(comment);

        return comment;
    }

    public Optional<List<Comment>> findAll(WriteBoardDto writeBoardDto) {
        log.info("writeBoardDto의 boardId= {} ", writeBoardDto.getBoardId());
        List<Comment> findComments = em.createQuery(
                        "select c from Comment c" +
                                " where board.id =: id"
                        , Comment.class)
                .setParameter("id", writeBoardDto.getBoardId())
                .getResultList();

        if (findComments.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(findComments);
    }


}
