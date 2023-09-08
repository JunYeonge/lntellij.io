package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.BindingResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.service.BoardService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public String findAll(@RequestParam(name = "page", defaultValue = "1") int page, Model model){
        // 한 페이지에 표시할 아이템 수와 페이지 번호 설정
        int pageSize = 10;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by("id").descending()); // 내림차순 정렬

        Page<BoardDto> boardDtoPage = boardService.findAll(pageable);

        model.addAttribute("boardList", boardDtoPage.getContent()); // 현재 페이지의 아이템 목록
        model.addAttribute("currentPage", page); // 현재 페이지 번호
        model.addAttribute("totalPages", boardDtoPage.getTotalPages()); // 전체 페이지 수
        model.addAttribute("isFirstPage", page == 1); // 현재 페이지가 첫 번째 페이지인지 여부
        model.addAttribute("isLastPage", page == boardDtoPage.getTotalPages()); // 현재 페이지가 마지막 페이지인지 여부

        return "/board/list";
    }

    @GetMapping("/post")
    public String showPostForm(Model model) {
        model.addAttribute("boardDTO", new BoardDto()); // 수정: board -> boardDTO로 변경
        return "/board/post";
    }

    @PostMapping("/post")
    public String postSave(@ModelAttribute("boardDto") @Valid BoardDto boardDto, BindingResult bindingResult,
                           @RequestParam("imageFile") List<MultipartFile> images) {
        if (bindingResult.hasErrors()) {
            return "/board/post";
        }

        List<String> imageUrls = boardService.saveImages(images); // 이미지 업로드 및 URL 리스트 가져오기

        // 게시물 DTO에 이미지 URL 리스트 설정
        boardDto.setImageUrls(imageUrls);

        boardService.save(boardDto, images); // 게시물 저장

        return "redirect:/board/list";
    }

    @GetMapping("list/{id}")
    public String findById(@PathVariable Long id, @NotNull Model model) {
        boardService.updateHits(id);
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);
        return "board/detail";
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
