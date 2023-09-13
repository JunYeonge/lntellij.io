package webtoon.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.board.BoardComment;

import java.util.List;


public interface CommentRepository extends JpaRepository<BoardComment, Long> {
    List<BoardComment> findByBoardId(Long boardId);
}
