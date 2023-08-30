package webtoon.controller;


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
public class BoardController {


    private final BoardService boardService;


    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("board/list")
    public String listBoards(Model model) {
        List<BoardDto> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        return "board/list"; // Thymeleaf 템플릿 경로
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