package webtoon.repository.board;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<BoardComment, Long> {

    List<BoardComment> findAllByBoardEntityOrderByIdDesc(Board board);

}
