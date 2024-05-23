package board.boardTest.service;

import board.boardTest.domain.Board;
import board.boardTest.domain.Member;
import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.repository.BoardRepository;
import board.boardTest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public Page<BoardDto> findAll(String type, String keyword, Pageable pageable) {
        if (type != null && keyword != null && !type.isEmpty() && !keyword.isEmpty()) {
            // 검색이 있는 경우
            return searchBoardList(type, keyword, pageable);
        } else {
            // 일반적인 목록 조회
            return allBoardList(pageable);
        }
    }

    @Transactional
    public BoardDto writeBoard(WriteBoardDto writeBoardDto) {
        String id = memberService.getSecurityId();
        Optional<Member> findMemberOptional = memberRepository.findById(id);
        writeBoardDto.setMember(findMemberOptional.get());
        Board saved = boardRepository.save(writeBoardDto);
        return Board.boardToBoardDto(saved);
    }

    public WriteBoardDto findById(Long id) {
        Optional<Board> findBoard = boardRepository.findById(id);

        if (findBoard.isEmpty()) {
            return null;
        }

        return Board.boardToWriteBoardDto(findBoard.get());
    }

    @Transactional
    public void updateView(Long boardId) {
        Optional<Board> findBoard = boardRepository.findById(boardId);

        if (findBoard.isPresent()) {
            Board board = findBoard.get();
            board.addViews();
        } else {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public void deleteById(Long boardId) {
        boardRepository.deleteById(boardId);
    }


    private Page<BoardDto> searchBoardList(String type, String keyword, Pageable pageable) {
        Optional<Page<Board>> findBoards;

        if ("title".equals(type)) {
            findBoards = boardRepository.findByTitleContaining(keyword, pageable);
        } else if ("writer".equals(type)) {
            findBoards = boardRepository.findByMember_Name(keyword, pageable);
        } else {
            return allBoardList(pageable);
        }

        return convertToBoardDtoPage(findBoards, pageable);
    }

    private Page<BoardDto> convertToBoardDtoPage(Optional<Page<Board>> findBoards, Pageable pageable) {
        if (findBoards.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<Board> boards = findBoards.get();
        List<BoardDto> boardDtos = boards.getContent().stream()
                .map(Board::boardToBoardDto)
                .collect(Collectors.toList());

        return new PageImpl<>(boardDtos, pageable, boards.getTotalElements());
    }
    private Page<BoardDto> allBoardList(Pageable pageable) {
        Optional<Page<Board>> findBoardsOptional = boardRepository.findAll(pageable);

        if (findBoardsOptional.isEmpty()) {
            return Page.empty(pageable);
        }

        Page<Board> boards = findBoardsOptional.get();

        // Board -> BoardDto 변환
        List<BoardDto> boardDtos = boards.getContent().stream()
                .map(Board::boardToBoardDto)
                .collect(Collectors.toList());

        // Page<BoardDto> 생성
        return new PageImpl<>(boardDtos, pageable, boards.getTotalElements());
    }
}
