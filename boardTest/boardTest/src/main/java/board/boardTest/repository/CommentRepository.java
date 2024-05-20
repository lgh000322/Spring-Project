package board.boardTest.repository;

import board.boardTest.domain.Comment;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.domain.commentdtos.CommentDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

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
                                " where board.id =: id and" +
                                " c.depth = :depth" +
                                " order by c.index asc, c.depth asc"
                        , Comment.class)
                .setParameter("id", writeBoardDto.getBoardId())
                .setParameter("depth", 1)
                .getResultList();

        if (findComments.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(findComments);
    }

    public Comment saveCommentTo(CommentDto commentDto) {
        Comment comment = Comment.commentDtoToComment(commentDto);
        em.persist(comment);

        return comment;
    }

    public Integer getLastIndex(Long boardId) {
        Integer maxIndex = em.createQuery(
                        "select max(c.index) from Comment c" +
                                " where c.board.id = :boardId", Integer.class)
                .setParameter("boardId", boardId)
                .getSingleResult();

        if (maxIndex == null) {
            return 1;
        }

        return maxIndex+1;

    }

    public Integer getDepth(Long boardId, Integer index) {
        Integer maxDepth = em.createQuery(
                        "select max(c.depth) from Comment c" +
                                " where c.board.id = :boardId" +
                                " and c.index = :index", Integer.class)
                .setParameter("boardId", boardId)
                .setParameter("index", index)
                .getSingleResult();

        if (maxDepth == null) {
            return 1;
        }

        return maxDepth + 1;
    }


}
