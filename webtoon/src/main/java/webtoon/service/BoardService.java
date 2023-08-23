package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardSearchDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;



import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;


    public List<Board> board() {
        return boardRepository.findAll();
    }

    public Board boardview(Long id){
        return boardRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public Page<Board> getAdminBoardPage(BoardSearchDto boardSearchDto, Pageable pageable){
        return boardRepository.getAdminBoardPage(boardSearchDto, pageable);
    }

}
