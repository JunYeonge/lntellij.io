package webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import webtoon.dto.BoardDto;
import webtoon.dto.BoardFormDto;
import webtoon.service.BoardService;

import java.util.Optional;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = "/board/list")
    public String boardPost(){
        return "board/list";
    }

    @GetMapping("/board/post")
    public String boardPost(Model model) {
        model.addAttribute("BoardFormDto",new BoardFormDto());
        return "board/post";
    }


    @GetMapping("/board/view")
    public String boardview(){
        return "board/view";
    }


}
