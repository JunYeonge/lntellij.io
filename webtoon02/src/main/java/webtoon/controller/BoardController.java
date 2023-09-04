package webtoon.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import webtoon.service.BoardService;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @GetMapping("/list")
    public String listForm(){
        return "/board/list";
    }

    @PostMapping("/list")
    public String list() {
        return null;
    }

}