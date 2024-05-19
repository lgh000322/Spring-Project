package board.boardTest.service;

import board.boardTest.domain.Board;
import board.boardTest.domain.Member;
import board.boardTest.domain.boarddtos.BoardDto;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.repository.BoardRepository;
import board.boardTest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public List<BoardDto> findAll() {
        Optional<List<Board>> findBoardsOptional = boardRepository.findAll();
        if (findBoardsOptional.isEmpty()) {
            return null;
        }

        List<Board> boards = findBoardsOptional.get();

        return boards.stream()
                .map(Board::boardToBoardDto)
                .collect(Collectors.toList());
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

}