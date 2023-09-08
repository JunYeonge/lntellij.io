package webtoon.controller;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import webtoon.dto.BoardDto;
import webtoon.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String paging(@PageableDefault(page = 1)Pageable pageble, Model model) {
        pageble.getPageNumber();
        Page<BoardDto> boardList = boardService.paging(pageble);

        int blockLimit = 5;
        int startPage = (((int)(Math.ceil((double)pageble.getPageNumber() / blockLimit))) -1) + blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        return "board/list";
    }

    @GetMapping("/post")
    public String post() {
        return "/board/post";
    }

    @PostMapping("/post")
    public String postSave(@ModelAttribute BoardDto boardDto) {
        boardService.save(boardDto);
        return "redirect:/board/list";
    }

    @GetMapping("list/{id}")
    public String findById(@PathVariable Long id, @NotNull Model model) {
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);
        return "/board/detail";
}

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "/board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model) {
        BoardDto boardDto1 = boardService.update(boardDto);
        model.addAttribute("board", boardDto1);
        return "board/detail";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }





}