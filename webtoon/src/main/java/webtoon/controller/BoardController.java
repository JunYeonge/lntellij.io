package webtoon.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class BoardController {

    private BoardService boardService;


    @GetMapping("/board/boardlist")
    public String list(){
        return "/board/boardlist";
    }

    @GetMapping("/board/boardwrite")
    public String boardWriteForm() {
        return "/board/boardwrite";
    }

    @PostMapping("/board/boardwrite")
    public String boardWritepro(BoardDto boardDto) {

        return "/board/boardwrite";
    }
}