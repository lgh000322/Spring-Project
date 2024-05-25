package board.boardTest.controller;

import board.boardTest.domain.Member;
import board.boardTest.domain.attachedfiledto.AttachedFileDto;
import board.boardTest.domain.attachedfiledto.ViewFileDto;
import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.domain.commentdtos.CommentDto;
import board.boardTest.domain.memberdtos.MemberDto;
import board.boardTest.service.BoardService;
import board.boardTest.service.CommentService;
import board.boardTest.service.AttachedFileService;
import board.boardTest.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;
    private final CommentService commentService;
    private final AttachedFileService fileService;

    @GetMapping
    public String boardView(@PageableDefault(page = 0, size = 3) Pageable pageable,
                            @RequestParam(name = "type", required = false) String type, //검색 타입
                            @RequestParam(name = "keyword", required = false) String keyword, // 검색 키워드
                            Model model) {
        Page<BoardDto> findBoards = boardService.findAll(type,keyword,pageable);

        int nowPage = 1; // 기본값 1로 설정
        if (!findBoards.isEmpty()) {
            nowPage = findBoards.getPageable().getPageNumber() + 1;
        }
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, findBoards.getTotalPages());

        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("BoardDto", findBoards);

        return "boards";
    }

    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable(name = "boardId") Long boardId,
                           Model model) {
        WriteBoardDto writeBoardDto = boardService.findById(boardId);
        List<CommentDto> findCommentDtos = commentService.getComments(writeBoardDto);
        ViewFileDto viewFileDto = fileService.getFilesByBoardId(boardId);
        writeBoardDto.setBoardId(boardId);

        String memberId = memberService.getSecurityId();
        MemberDto findMemberDto = memberService.findMemberById(memberId);

        //조회수 증가
        boardService.updateView(writeBoardDto.getBoardId());

        //모델 추가
        model.addAttribute("ViewFileDto", viewFileDto);
        model.addAttribute("CommentDto", findCommentDtos);
        model.addAttribute("WriteBoardDto", writeBoardDto);
        model.addAttribute("LoginMemberName", findMemberDto.getName());
        return "boardView";
    }

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource downloadImage(@PathVariable(name = "fileName") String savedName) throws MalformedURLException {
        log.info("savedName={} ", savedName);
        return new UrlResource("file:" + fileService.getFullPath(savedName));
    }

    @GetMapping("/attach/{savedName}")
    public ResponseEntity<Resource> downloadAttach(@PathVariable(name = "savedName") String savedName) throws MalformedURLException {
        String findOriginalName = fileService.findOriginalNameBySavedName(savedName);
        UrlResource resource = new UrlResource("file:" + fileService.getFullPath(savedName));

        log.info("업로드 파일 이름={})", findOriginalName);
        String encodedUploadFileName = UriUtils.encode(findOriginalName, StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\";";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
    }

    @GetMapping("/write")
    public String writeBoard(@ModelAttribute(name = "WriteBoardDto") WriteBoardDto writeBoardDto) {
        String id = memberService.getSecurityId();
        MemberDto findMemberDto = memberService.findMemberById(id);

        writeBoardDto.setMember(Member.memberDtoToMember(findMemberDto));

        return "board-write";
    }

    @PostMapping("/write")
    public String writeBoardProcess(@RequestParam(name = "file",required = false) List<MultipartFile> files,
                                    @ModelAttribute(name = "WriteBoardDto") WriteBoardDto writeBoardDto,
                                    RedirectAttributes redirectAttributes) throws IOException {
        BoardDto boardDto = boardService.writeBoard(writeBoardDto);
        redirectAttributes.addAttribute("id", boardDto.getId());

        if (!files.isEmpty()) {
            fileService.save(files, boardDto.getId());
        }
        return "redirect:/board/{id}";
    }

    @PostMapping("/delete/{boardId}")
    public String deleteBoardById(@PathVariable(name = "boardId") Long boardId) {
        fileService.deleteAttachedFilesFromDisk(boardId);
        boardService.deleteById(boardId);
        return "redirect:/board";
    }
}
