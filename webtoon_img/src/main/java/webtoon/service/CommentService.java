package webtoon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;
import webtoon.repository.board.BoardRepository;
import webtoon.repository.board.CommentRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository; // 게시물과 댓글 간의 관계를 설정하기 위해 게시물 레포지토리를 주입

    public void addComment(Long boardId, BoardComment boardComment) {
        // 게시물 ID를 기반으로 해당 게시물을 찾습니다.
        Optional<Board> boardOptional = boardRepository.findById(boardId);

        // 게시물을 찾지 못하면 예외 처리
        if (!boardOptional.isPresent()) {
            throw new EntityNotFoundException("게시물을 찾을 수 없습니다.");
        }

        // 게시물을 찾았으면 댓글을 게시물과 연결하고 저장합니다.
        Board board = boardOptional.get();
        boardComment.setBoard(board); // 댓글과 게시물 간의 연관 관계 설정
        boardComment.setCreatedAt(LocalDateTime.now()); // 댓글 작성 시간 설정

        commentRepository.save(boardComment); // 댓글 저장
    }


}

