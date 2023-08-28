package spring_study.board_crud.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import spring_study.board_crud.domain.Board;
import spring_study.board_crud.dto.BoardDto;
import spring_study.board_crud.service.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardApiController {
    private final BoardService boardService;

    @GetMapping("/api/board-list")
    public WrapperClass board_list() {
        List<Board> boardList = boardService.findBoards();
        List<BoardDto> boardDtoList;
        boardDtoList = boardList.stream().map(b -> new BoardDto(b)).collect(Collectors.toList());
        return new WrapperClass(boardDtoList);
    }

    @GetMapping("api/board-detail/{boardId}")
    public WrapperClass board_detail(@PathVariable("boardId") Long boardId) {
        Board board = boardService.findOne(boardId);
        BoardDto boardDto = new BoardDto(board);
        return new WrapperClass(boardDto);
    }
}
