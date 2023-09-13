package webtoon.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import webtoon.dto.BoardDto;
import webtoon.service.BoardService;

import java.util.List;

@Controller
@AllArgsConstructor
public class MainController {
    private static final int pageSize = 7; // 한 페이지에 표시될 게시물의 수
    private final BoardService boardService;
    @GetMapping(value = "/main")
    public String main(Model model, Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber(), pageSize, Sort.by("id").descending());
        Page<BoardDto> boardDtoPage = boardService.findAll(pageable);

        model.addAttribute("boardTitles", boardDtoPage ); // 제목만 모델에 추가합니다
        return "main/main";
    }
    @GetMapping(value = "/main/allWebtoon")
    public String allwebtoon() {
        return "main/allWebtoon";
    }

    @GetMapping(value = "/main/dailyWebtoon")
    public String dailyWebtoon() {
        return "main/dailyWebtoon";
    }
    @GetMapping(value = "/main/myPage")
    public String myPage() {
        return "main/myPage";
    }

}
