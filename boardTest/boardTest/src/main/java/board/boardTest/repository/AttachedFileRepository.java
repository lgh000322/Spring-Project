package board.boardTest.repository;

import board.boardTest.domain.AttachedFile;
import board.boardTest.domain.attachedfiledto.AttachedFileDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AttachedFileRepository {

    private final EntityManager em;

    public AttachedFile save(AttachedFileDto attachedFileDto) {
        AttachedFile file = AttachedFile.fileDtoToFile(attachedFileDto);
        em.persist(file);

        return file;
    }

    public Optional<String> findOriginalNameBySavedName(String storedName) {
        String findOriginalName = em.createQuery("select a.originalName from AttachedFile a" +
                        " where a.storedName = :storedName", String.class)
                .setParameter("storedName", storedName)
                .getSingleResult();

        if (findOriginalName == null) {
            return Optional.empty();
        }

        return Optional.of(findOriginalName);
    }
    public Optional<List<AttachedFile>> findByBoardId(Long boardId) {
        List<AttachedFile> findAttachedFiles = em.createQuery("select a from AttachedFile a" +
                        " join a.board b" +
                        " where b.id = :boardId", AttachedFile.class)
                .setParameter("boardId", boardId)
                .getResultList();

        if (findAttachedFiles.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(findAttachedFiles);
    }



}
