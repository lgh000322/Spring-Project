package board.boardTest.service;

import board.boardTest.domain.AttachedFile;
import board.boardTest.domain.Board;
import board.boardTest.domain.attachedfiledto.AttachedFileDto;
import board.boardTest.domain.attachedfiledto.UploadFile;
import board.boardTest.domain.attachedfiledto.ViewFileDto;
import board.boardTest.repository.BoardRepository;
import board.boardTest.repository.AttachedFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

    public String findOriginalNameBySavedName(String savedName) {
        Optional<String> findOriginalName = attachedFileRepository.findOriginalNameBySavedName(savedName);

        if (findOriginalName.isEmpty()) {
            return null;
        }

        return findOriginalName.get();
    }
    public ViewFileDto getFilesByBoardId(Long boardId) {
        Optional<List<AttachedFile>> findFilesOptional = attachedFileRepository.findByBoardId(boardId);

        if (findFilesOptional.isEmpty()) {
            return null;
        }

        List<AttachedFile> attachedFiles = findFilesOptional.get();
        List<UploadFile> imageFiles = new ArrayList<>();
        List<UploadFile> textFiles = new ArrayList<>();
        ViewFileDto viewFileDto = new ViewFileDto();

        for (AttachedFile attachedFile : attachedFiles) {
            String extension = getExtension(attachedFile.getStoredName());
            UploadFile uploadFile = new UploadFile();
            setImageFilesAndTextFiles(imageFiles, textFiles, attachedFile, extension, uploadFile);
        }

        viewFileDto.setImageFileNames(imageFiles);
        viewFileDto.setTextFileNames(textFiles);

        return viewFileDto;
    }

    private void setImageFilesAndTextFiles(List<UploadFile> imageFiles, List<UploadFile> textFiles, AttachedFile attachedFile, String extension, UploadFile uploadFile) {
        if (extension.equals("txt")) {
            getFileInfo(attachedFile, uploadFile);
            textFiles.add(uploadFile);
        } else {
            getFileInfo(attachedFile, uploadFile);
            imageFiles.add(uploadFile);
        }
    }

    private void getFileInfo(AttachedFile attachedFile, UploadFile uploadFile) {
        uploadFile.setOriginalName(attachedFile.getOriginalName());
        uploadFile.setSavedName(attachedFile.getStoredName());
    }


    private List<AttachedFileDto> doSaveProcess(List<MultipartFile> fileList, Long boardId) throws IOException {
        List<AttachedFileDto> returnList = new ArrayList<>();

        for (MultipartFile multipartFile : fileList) {
            String extension = "."+getExtension(multipartFile.getOriginalFilename());
            log.info("extension={}", extension);

            AttachedFileDto attachedFileDto = getFileDto(multipartFile, boardId,extension);
            AttachedFile savedFile = attachedFileRepository.save(attachedFileDto);

            String fullPath=fileDir+attachedFileDto.getStoredName();
            multipartFile.transferTo(new File(fullPath));

            returnList.add(AttachedFile.fileToFileDto(savedFile));
        }
        return returnList;
    }


    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }
    public String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }


    private AttachedFileDto getFileDto(MultipartFile file, Long boardId,String extension) {
        AttachedFileDto attachedFileDto = new AttachedFileDto();
        attachedFileDto.setOriginalName(file.getOriginalFilename());
        attachedFileDto.setStoredName(makeFileStoredName()+extension);
        Optional<Board> findBoard = boardRepository.findById(boardId);
        findBoard.ifPresent(f -> attachedFileDto.setBoard(findBoard.get()));
        return attachedFileDto;
    }

    private String makeFileStoredName() {
        return UUID.randomUUID().toString();
    }


}
