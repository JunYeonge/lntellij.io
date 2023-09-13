package webtoon.controller;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import webtoon.dto.BoardDto;
import webtoon.dto.CommentDto;
import webtoon.service.BoardService;
import webtoon.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/list")
    public String findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "query", required = false) String query, // 검색어를 받아옵니다.
            Model model
    ) {
        int pageSize = 3;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());

        Page<BoardDto> boardDtoPage;

        if (query != null && !query.isEmpty()) {
            // 검색어(query)가 제공되면 검색 결과를 가져옵니다.
            boardDtoPage = boardService.findBySearchQuery(query, pageable);
        } else {
            // 검색어가 없으면 모든 게시물을 가져옵니다.
            boardDtoPage = boardService.findAll(pageable);
        }

        model.addAttribute("boardList", boardDtoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardDtoPage.getTotalPages());
        model.addAttribute("isFirstPage", page == 1);
        model.addAttribute("isLastPage", page == boardDtoPage.getTotalPages());
        model.addAttribute("query", query); // 검색어를 다시 폼에 표시하기 위해 모델에 추가

        return "/board/list";
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
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault(page=1) Pageable pageable) {
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);

        /* 댓글 목록 가져오기*/
        List<CommentDto> commentDtoList = commentService.findAll(id);

        model.addAttribute("commentList",commentDtoList);
        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());
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