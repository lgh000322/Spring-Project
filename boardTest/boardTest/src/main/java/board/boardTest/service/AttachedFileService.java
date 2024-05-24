package board.boardTest.service;

import board.boardTest.domain.AttachedFile;
import board.boardTest.domain.Board;
import board.boardTest.domain.attachedfiledto.AttachedFileDto;
import board.boardTest.repository.BoardRepository;
import board.boardTest.repository.AttachedFileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AttachedFileService {

    @Value("${file.dir}")
    private String fileDir;

    private final AttachedFileRepository attachedFileRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public List<AttachedFileDto> save(List<MultipartFile> fileList, Long boardId) throws IOException {
        List<AttachedFileDto> returnList = doSaveProcess(fileList, boardId);
        return returnList;
    }


    private List<AttachedFileDto> doSaveProcess(List<MultipartFile> fileList, Long boardId) throws IOException {
        List<AttachedFileDto> returnList = new ArrayList<>();

        for (MultipartFile multipartFile : fileList) {
            String fileName = multipartFile.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1);

            AttachedFileDto attachedFileDto = getFileDto(multipartFile, boardId);
            AttachedFile savedFile = attachedFileRepository.save(attachedFileDto);

            String fullPath=fileDir+attachedFileDto.getStoredName()+extension;
            multipartFile.transferTo(new File(fullPath));

            returnList.add(AttachedFile.fileToFileDto(savedFile));
        }
        return returnList;
    }


    private AttachedFileDto getFileDto(MultipartFile file, Long boardId) {
        AttachedFileDto attachedFileDto = new AttachedFileDto();
        attachedFileDto.setOriginalName(file.getOriginalFilename());
        attachedFileDto.setStoredName(getFileStoredName());
        Optional<Board> findBoard = boardRepository.findById(boardId);
        findBoard.ifPresent(f -> attachedFileDto.setBoard(findBoard.get()));
        return attachedFileDto;
    }

    private String getFileStoredName() {
        return UUID.randomUUID().toString();
    }


}
