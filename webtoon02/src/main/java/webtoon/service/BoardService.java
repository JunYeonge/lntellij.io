package webtoon.service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import webtoon.repository.board.BoardRepository;


@AllArgsConstructor
@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;


}

