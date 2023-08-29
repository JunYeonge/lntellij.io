package toyproject.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toyproject.board.domain.Board;
import toyproject.board.domain.BoardComment;

import java.util.List;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {

    @Query("select c from BoardComment c where c.board.id = :id")
    List<BoardComment> findCommentsBoardId(@Param("id") Long id);


}
