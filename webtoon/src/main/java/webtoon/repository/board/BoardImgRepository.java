package webtoon.repository.board;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.board.BoardImg;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long > {
    List<BoardImg> findByBoardIdOrderByIdAsc(Long itemId);
}
