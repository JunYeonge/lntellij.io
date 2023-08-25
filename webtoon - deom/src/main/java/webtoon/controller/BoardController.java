package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webtoon.dto.BoardDto;
import webtoon.service.BoardService;

import java.util.List;


@Controller
@RequestMapping
@RequiredArgsConstructor
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @GetMapping("/board/list")
    public String findAll(Model model){
        List<BoardDto> boardDtoList = boardService.findAll();
        model.addAttribute("boardList",boardDtoList);
        return "board/list";
    }

    @GetMapping("/board/post")
    public String post(){
        return "board/post";
    }

    @PostMapping("/board/post")
    public String boardPost(@ModelAttribute BoardDto boardDto){
        Long id = boardService.save(boardDto);
        return "redirect:/";
    }
    @GetMapping("/board/view")
    public String view(){
        return "board/view";
    }





}
