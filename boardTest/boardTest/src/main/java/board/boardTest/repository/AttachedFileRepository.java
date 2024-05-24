package board.boardTest.repository;

import board.boardTest.domain.AttachedFile;
import board.boardTest.domain.attachedfiledto.AttachedFileDto;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AttachedFileRepository {

    private final EntityManager em;

    public AttachedFile save(AttachedFileDto attachedFileDto) {
        AttachedFile file = AttachedFile.fileDtoToFile(attachedFileDto);
        em.persist(file);

        return file;
    }

}
