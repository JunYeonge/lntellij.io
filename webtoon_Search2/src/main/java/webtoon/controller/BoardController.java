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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            @RequestParam(name = "keyword", required = false) String keyword,
            Model model
    ) {
        int pageSize = 12;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending());

        Page<BoardDto> boardDtoPage;
        if (keyword != null && !keyword.isEmpty()) {
            boardDtoPage = boardService.search(keyword, pageable);
        } else {
            boardDtoPage = boardService.findAll(pageable);
        }

        model.addAttribute("boardList", boardDtoPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", boardDtoPage.getTotalPages());
        model.addAttribute("isFirstPage", page == 1);
        model.addAttribute("isLastPage", page == boardDtoPage.getTotalPages());
        model.addAttribute("keyword", keyword); // 검색어를 뷰로 전달

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
    public String findById(@PathVariable(value = "id") Long id, Model model,
                           @PageableDefault(page = 1) Pageable pageable,
                           HttpServletRequest request) {

        HttpSession session = request.getSession();
        String sessionKey = "board_" + id;

        if (session.getAttribute(sessionKey) == null) {
            // 세션에 조회 여부가 없으면 조회수 증가 및 세션에 기록
            boardService.updateHits(id);
            session.setAttribute(sessionKey, true);

        }

        BoardDto boardDto = boardService.findById(id);

        /* 댓글 목록 가져오기 */
        List<CommentDto> commentDtoList = commentService.findAll(id);
        model.addAttribute("commentList", commentDtoList);
        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());
        return "/board/detail";
    }

//    @GetMapping("list/{id}")
//    public String findById(@PathVariable(value = "id") Long id, Model model,
//                           @PageableDefault(page = 1) Pageable pageable,
//                           HttpServletRequest request) {
//
//    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "board/update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model) {
        BoardDto boardDto1 = boardService.update(boardDto);
        model.addAttribute("board", boardDto1);
        return "redirect:/board/list/" + boardDto.getId();
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/list";
    }




}