package webtoon.controller;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webtoon.dto.BoardDto;
import webtoon.service.BoardService;


import java.util.List;

@Controller
@AllArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/board/list")
    public String listBoards(Model model, @RequestParam(value = "page", defaultValue = "1") Integer pageNum) {
        List<BoardDto> boardList = boardService.getBoardlist(pageNum);
        Integer[] pageList = boardService.getPageList(pageNum);

        model.addAttribute("pageList",pageList);
        model.addAttribute("boardList", boardList);
        return "board/list";
    }

    @GetMapping("/board/post")
    public String showCreateForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "board/post"; // Thymeleaf 템플릿 경로
    }

    @PostMapping("/board/post")
    public String createBoard(@ModelAttribute BoardDto boardDto) {
        boardService.createBoard(boardDto);
        return "redirect:/list";
    }
}