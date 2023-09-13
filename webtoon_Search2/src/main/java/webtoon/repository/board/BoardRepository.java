package webtoon.repository.board;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long> {
    @Modifying
    @Query(value = "update Board b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long Id);

    Page<Board> findByBoardTitleContainingIgnoreCase(String keyword, Pageable pageable);
}
