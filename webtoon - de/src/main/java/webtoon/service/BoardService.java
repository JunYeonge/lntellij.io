package webtoon.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.repository.board.BoardRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional

public class BoardService {
    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public BoardService(BoardRepository boardRepository, ModelMapper modelMapper) {
        this.boardRepository = boardRepository;
        this.modelMapper = modelMapper;
    }



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

    private BoardDto convertToDto(Board board) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(board, BoardDto.class);
    }

    private Board convertToEntity(BoardDto boardDto) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(boardDto, Board.class);
    }
}
