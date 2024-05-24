package board.boardTest.domain;

import board.boardTest.domain.attachedfiledto.AttachedFileDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class AttachedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attached_file_id")
    private Long id;

    @Column(name = "attached_file_original_name")
    private String originalName;

    @Column(name = "attached_file_stored_name")
    private String storedName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //===================================== 연관관계 편의 메소드 ========================================

    protected AttachedFile() {
    }

    @Builder
    public AttachedFile(String originalName, String storedName, Board board) {
        this.originalName = originalName;
        this.storedName = storedName;
        this.board = board;
    }



    public static AttachedFile fileDtoToFile(AttachedFileDto attachedFileDto) {
        AttachedFile file = AttachedFile.builder()
                .originalName(attachedFileDto.getOriginalName())
                .storedName(attachedFileDto.getStoredName())
                .board(attachedFileDto.getBoard())
                .build();

        return file;
    }

    public static AttachedFileDto fileToFileDto(AttachedFile file) {
        AttachedFileDto attachedFileDto = new AttachedFileDto();
        attachedFileDto.setBoard(file.getBoard());
        attachedFileDto.setStoredName(file.getStoredName());
        attachedFileDto.setOriginalName(file.getOriginalName());

        return attachedFileDto;
    }
}
