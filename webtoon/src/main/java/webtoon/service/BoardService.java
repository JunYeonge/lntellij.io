package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.repository.board.BoardRepository;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private BoardRepository boardRepository;



    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }





}
