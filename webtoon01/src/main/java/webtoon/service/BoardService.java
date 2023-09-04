package webtoon.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import webtoon.dto.BoardFormDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;

import java.util.List;


@AllArgsConstructor
@Service
public class BoardService {
    private BoardRepository boardRepository;


    public void saveBoard(BoardFormDto boardFormDto, List<MultipartFile> boardImgFilesList) {
        Board board = boardFormDto.createBoard();
        boardRepository.save(board);
    }
}

