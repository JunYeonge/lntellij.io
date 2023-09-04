package webtoon.service;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardDto;
import webtoon.dto.BoardFormDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public List<BoardDto> getAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public void createBoard(BoardDto boardDto) {
        Board board = convertToEntity(boardDto);
        boardRepository.save(board);
    }
    public BoardFormDto getBoardDtl(Long boardId) {
        List<Board> boardList = boardRepository.findByboardId(boardId);
        List<BoardDto> boardDtoList = new ArrayList<>();
        for (Board board : boardList) {
            BoardDto boardDto = BoardDto.of(board);
            boardDtoList.add(boardDto);
        }
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityNotFoundException::new);
        BoardFormDto boardFormDto = BoardFormDto.of(board);
        boardFormDto.setBoardDtoList(boardDtoList);
        return boardFormDto;
    }
    private BoardDto convertToDto(Board board) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(board, BoardDto.class);
    }

    private Board convertToEntity(BoardDto boardDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(boardDto, Board.class);
    }
}
