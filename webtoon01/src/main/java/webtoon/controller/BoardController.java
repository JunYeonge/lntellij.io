package webtoon.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import webtoon.dto.BoardDto;
import webtoon.dto.BoardFormDto;
import webtoon.service.BoardService;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;



    @GetMapping("/board/list")
    public String list(){
        return "/board/list";
    }

    @GetMapping(value = "/board/post")
    public String boardWriteForm(Model model) {
        model.addAttribute("boardFormDto", new BoardFormDto());
        return "/board/post";
    }

    @PostMapping(value = "/board/write")
    public String boardNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
                           Model model, @RequestParam("boardImg")List<MultipartFile> boardImgFilesList) {
        if(bindingResult.hasErrors()) {
            return "board/list";
        }
        if((boardImgFilesList.isEmpty() || boardImgFilesList.get(0).isEmpty()) &&
                (boardFormDto.getId() == null || boardFormDto.getId().isEmpty)) {
            model.addAttribute("errorMessage","이미지1개를 추가해주세요");
            return "board/list";
        }
        try{
            boardService.saveBoard(boardFormDto, boardImgFilesList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "게시판 등록 중 에러가 발생하였습니다.");
            return "board/list";
        }
        return "redirect:/board/list";
    }
}