package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.api.TimeController;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;



@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    TimeController timeController = new TimeController();
    @Autowired
    private BoardRepository boardRepository;

    public void write(Board board){
        timeController.getServerTime();
        boardRepository.save(board);
    }
}
