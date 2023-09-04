package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String getBoardIndexPage(Model model) {
        model.addAttribute("result", boardService.findAll());
        return "/board/index";
    }

    @GetMapping("/board/write")
    public String getBoardWritePage(Model model, Board.RequestDto requestDto) {

        if (requestDto.getId() != null) {
            model.addAttribute("info", boardService.findById(requestDto.getId()));
        }

        return "/board/write";
    }

    @PostMapping("/board/save")
    public String save(Model model, Board.RequestDto requestDto) {
        String url = "/error/blank";

        if (boardService.save(requestDto) > 0) {
            url = "redirect:/board";
        }

        return url;
    }

    @PostMapping("/board/update")
    public String update(Model model, Board.RequestDto requestDto) {
        String url = "/error/blank";

        if (boardService.updateBoard(requestDto) > 0) {
            url = "redirect:/board";
        }

        return url;
    }

    @PostMapping("/board/delete")
    public String delete(Model model, Board.RequestDto requestDto) {
        boardService.deleteBoard(requestDto);
        return "redirect:/board";
    }
}