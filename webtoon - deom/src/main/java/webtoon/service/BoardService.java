package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Long save(BoardDto boardDto) {
        Long savedId =  boardRepository.save(Board.toSaveEntity(boardDto)).getId();
        return savedId;
    }

    public List<BoardDto> findAll() {
       List<Board> boardList = boardRepository.findAll();
       List<BoardDto> boardDtoList = new ArrayList<>();
       for (Board board : boardList) {
           boardDtoList.add(BoardDto.boardDto(board)); /* entity 를 DTO 객체로 변환하고 for 문을 사용해서 추가 */
       }
       return boardDtoList;
    }
}
