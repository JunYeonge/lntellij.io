package webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webtoon.dto.BoardFormDto;
import webtoon.dto.BoardSearchDto;
import webtoon.entity.board.Board;
import webtoon.service.BoardService;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping(value = {"/board/list", "/board/list/{page}"})
    public String boardList(BoardSearchDto boardSearchDto, @PathVariable("page")Optional<Integer> page,
                            Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 25);

        Page<Board> boards = boardService.getAdminBoardPage(boardSearchDto,pageable);
        model.addAttribute("boards",boards);
        model.addAttribute("boardSearchDto",boardSearchDto);
        model.addAttribute("maxPage",10);
        return "board/list";
    }

    @GetMapping("/board/post")
    public String boardPost(Model model) {
        model.addAttribute("BoardFormDto",new BoardFormDto());
        return "board/post";
    }

//    @PostMapping(value = "/board/post")
//    public String postNew(@Valid BoardFormDto boardFormDto, BindingResult bindingResult,
//                          Model model){
//
//    }


    @GetMapping("/board/view")
    public String boardview(Model model, Long id){
        model.addAttribute("board",boardService.boardview(id));
        return "board/view";

    }


}
