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

import javax.swing.text.html.Option;
import java.util.ArrayList;
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

        if (findMember.isEmpty()) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        if (findBoard.isEmpty()) {
            throw new RuntimeException("게시글을 찾을 수 없습니다.");
        }

        commentDto.setMember(findMember.get());
        commentDto.setBoard(findBoard.get());

        Comment savedComment = commentRepository.save(commentDto);

        return Comment.commentToCommentDto(savedComment);
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
