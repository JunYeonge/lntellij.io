package webtoon.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.board.BoardComment;


public interface CommentRepository extends JpaRepository<BoardComment, Long> {
}
