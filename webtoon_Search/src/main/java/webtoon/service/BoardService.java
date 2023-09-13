package webtoon.service;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final HttpServletRequest request;

    public void save(BoardDto boardDto) {
        Board board = Board.toSaveEntity(boardDto);
        boardRepository.save(board);
    }

    public Page<BoardDto> findAll(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(BoardDto::toBoardDto);
    }

    @Transactional
    public void updateHits(Long id) {
        String viewedAttribute = "viewed_" + id;
        if (request.getSession().getAttribute(viewedAttribute) == null) {
            boardRepository.updateHits(id);
            request.getSession().setAttribute(viewedAttribute, true);
        }
    }

    public BoardDto findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            BoardDto boardDto = BoardDto.toBoardDto(board);
            return boardDto;
        } else {
            return null;
        }
    }

    public BoardDto update(BoardDto boardDto) {
        Board board = Board.toUpdateEntity(boardDto);
        boardRepository.save(board);
        return findById(board.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

//    public void saveBoard(Board board) {
//        boardRepository.save(board);
//    }

//    public Page<BoardDto> searchBoards(String keyword, Pageable pageable) {
//        Page<Board> searchResults = boardRepository.findByBoardTitleContainingIgnoreCase(keyword, pageable);
//        return searchResults.map(BoardDto::fromEntity);
//    }
//
//    private List<BoardDto> convertToDtoList(List<Board> boards) {
//        return boards.stream()
//                .map(BoardDto::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    public Page<BoardDto> searchBoardsByKeyword(String keyword, Pageable pageable) {
//        Page<Board> searchResults = boardRepository.findByBoardTitleContainingIgnoreCase(keyword, pageable);
//        return searchResults.map(board -> {
//            BoardDto boardDto = BoardDto.fromEntity(board);
//            boardDto.setBoardWriter(board.getBoardWriter());
//            boardDto.setBoardRegTime(board.getRegTime());
//            boardDto.setBoardHits(board.getBoardHits());
//            return boardDto;
//        });
//    }


    public Page<BoardDto> findBySearchQuery(String query, Pageable pageable) {
        return boardRepository.findByTitleContainingOrContentContaining(query, pageable);
    }
}


