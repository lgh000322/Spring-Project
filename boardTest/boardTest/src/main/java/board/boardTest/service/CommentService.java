package board.boardTest.service;

import board.boardTest.domain.Board;
import board.boardTest.domain.Comment;
import board.boardTest.domain.Member;
import board.boardTest.domain.boarddtos.WriteBoardDto;
import board.boardTest.domain.commentdtos.CommentDto;
import board.boardTest.repository.BoardRepository;
import board.boardTest.repository.CommentRepository;
import board.boardTest.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public CommentDto save(CommentDto commentDto) {
        Long id = commentDto.getBoardId();
        Optional<Board> findBoard = boardRepository.findById(id);

        String memberName = commentDto.getMemberName();
        Optional<Member> findMember = memberRepository.findByName(memberName);

        Integer lastIndex = commentRepository.getLastIndex(id);

        if (findMember.isEmpty()) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        if (findBoard.isEmpty()) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        commentDto.setMember(findMember.get());
        commentDto.setBoard(findBoard.get());
        commentDto.setIndex(lastIndex);
        commentDto.setDepth(1);

        Comment savedComment = commentRepository.save(commentDto);

        return Comment.commentToCommentDto(savedComment);
    }

    @Transactional
    public CommentDto saveCommentTo(CommentDto commentDto) {
        String memberName = commentDto.getMemberName();
        Optional<Member> findMember = memberRepository.findByName(memberName);

        Long boardId = commentDto.getBoardId();
        Optional<Board> findBoard = boardRepository.findById(boardId);

        Integer index = commentDto.getIndex();

        if (findMember.isEmpty()) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        if (findBoard.isEmpty()) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        commentDto.setBoard(findBoard.get());
        commentDto.setMember(findMember.get());
        commentDto.setDepth(commentRepository.getDepth(boardId,index));

        return Comment.commentToCommentDto(commentRepository.saveCommentTo(commentDto));
    }

    public List<CommentDto> getComments(WriteBoardDto writeBoardDto) {
        Optional<List<Comment>> findComments = commentRepository.findAll(writeBoardDto);

        if (findComments.isEmpty()) {
            return null;
        }

        return findComments.get().stream()
                .map(Comment::commentToCommentDto)
                .collect(Collectors.toList());
    }




}
