package toyproject.board.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.board.domain.Board;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

        List<Board> findAll();

        Page<Board> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);

//        Board findById();
}
