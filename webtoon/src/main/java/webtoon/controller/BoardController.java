package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webtoon.entity.board.Board;
import webtoon.service.BoardService;



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
    public String boardWritepro(Board board) {
        boardService.write(board);
        return "/board/boardwrite";
    }


}
