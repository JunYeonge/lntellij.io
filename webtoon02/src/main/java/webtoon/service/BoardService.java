package webtoon.service;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    public void save(BoardDto boardDto) {
        Board board = Board.toSaveEntity(boardDto);
        boardRepository.save(board);
    }

    public List<BoardDto> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board: boardList) {
            boardDtoList.add(BoardDto.toBoardDto(board));
        }
        return boardDtoList;
    }

    @Transactional
    public void updateHits(Long id) {
    boardRepository.updateHits(id);
    }

    public BoardDto findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isPresent()) {
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

    public Page<BoardDto> paging(Pageable pageble) {
        int page = pageble.getPageNumber() - 1;
        int pageLimit = 10; // 한 페이지에 보여줄 글 갯수
        // 한페이지당 25개씩 글을 보여주고 정렬기준은 ID 가준으로 내림차순 정렬
        Page<Board> board = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        //DESC : 내림차순

    Page<BoardDto> boardDtos = board.map(board1 -> new BoardDto(board1.getBoardWriter(), board1.getBoardTitle(), board1.getBoardHits(), board1.getRegTime()));
    return boardDtos;
    }
}

