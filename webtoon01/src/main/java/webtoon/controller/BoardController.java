package webtoon.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webtoon.dto.BoardDto;
import webtoon.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/board/list")
    public String list(){
        return "/board/list";
    }

    @GetMapping("/board/write")
    public String boardWriteForm(Module module) {
        return "/board/write";
    }

    @PostMapping("/board/write")
    public String boardWritepro(BoardDto boardDto) {
        return "redirect:/board/list";
    }
}