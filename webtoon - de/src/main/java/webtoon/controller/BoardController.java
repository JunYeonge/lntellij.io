package webtoon.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webtoon.dto.BoardDto;
import webtoon.service.BoardService;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class BoardController {

    @Autowired
    private BoardService boardService;


    @GetMapping("/board/list")
    public String list() {
        return "board/list.html";
    }

    @GetMapping("/board/post")
    public String post() {
        return "board/post.html";
    }
}