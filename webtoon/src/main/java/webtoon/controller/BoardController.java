package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.service.BoardService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping
public class BoardController {
    @Autowired
    private BoardService boardService;


    @GetMapping("/board/boardwrite")
    public String boardWriteForm() {
        return "board/boardwrite";
    }

    @PostMapping("/board/boardwrite")
    public String boardWritepro(Board board, Model model) {
        List<BoardDto> boardDtoList = new ArrayList<>();

        for (int i = 0; i <= 5000; i++) {
            BoardDto boardDto = new BoardDto();
            boardDto.setId(board.getId());
            boardDto.setUser_id(board.getUser_id());
            boardDto.setTitle(board.getTitle());
            boardDto.setContent(board.getContent());
            boardDto.setNickname(board.getNickname());
            boardDto.setView_count(board.getView_count());
            boardDto.setRegTime(LocalDateTime.now());
            boardDtoList.add(boardDto);
        }
        model.addAttribute("boardDtoList",boardDtoList);
        return "/board/boardwrite";
    }
}
