package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;



@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public void saveBoard(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        board.setUsername(boardDto.getUsername());
        // viewCount, createdAt, updatedAt은 자동으로 설정됨
        boardRepository.save(board);
    }
}


}
