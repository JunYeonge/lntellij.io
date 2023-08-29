package toyproject.board.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyproject.board.domain.Board;
import toyproject.board.domain.BoardComment;
import toyproject.board.domain.Member;
import toyproject.board.dto.BoardCommentDto;
import toyproject.board.dto.BoardDto;
import toyproject.board.repository.BoardCommentRepository;
import toyproject.board.repository.BoardRepository;
import toyproject.board.repository.MemberRepository;
import toyproject.board.service.BoardCommentService;
import toyproject.board.service.BoardService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final BoardRepository boardRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final BoardCommentService boardCommentService;
    private final MemberRepository memberRepository;

    @GetMapping("/boardForm")
    public String addBoard() {
        return "/board/boardForm";
    }

    //
    @PostMapping("/boardForm")
    public String createBoard(@ModelAttribute BoardDto boardDto, Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        boardDto.setCreatedBy(username);
        boardDto.setCountVisit(1L);
        boardService.saveBoard(boardDto);

        return "redirect:/";
    }


    @GetMapping("/boardList")
    public String boardList(Model model, @PageableDefault(size = 10) Pageable pageable,
                            @RequestParam(required = false, defaultValue = "") String searchText) {

        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);

        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 1);
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 3);

        model.addAttribute("boards", boards);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/boardList";
    }

    @GetMapping("/boardContent/{id}")
    public String boardContent(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.findById(id).get();
        List<BoardComment> comments = boardCommentRepository.findCommentsBoardId(id);

//        BoardDto boardDto = BoardDto.builder()
//                .countVisit(board.getCountVisit()+1)
//                .build();
//
//        boardService.updateVisit(board.getId(), boardDto);

//        boardService.countVisitLogic(id);

        model.addAttribute(board);
        model.addAttribute("comments", comments);

        return "/board/boardContent";
    }

    @PostMapping("/boardContent/{id}")
    public String addComment(@PathVariable("id") Long id, @ModelAttribute BoardCommentDto boardCommentDto, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDetails userDetails = (UserDetails) principal;
        String username = userDetails.getUsername();

        Board board = boardRepository.findById(id).get();
        Member member = memberRepository.findByUsername(username);


        LocalDateTime now = LocalDateTime.now();


        BoardCommentDto boardCommentDtoSet = BoardCommentDto.builder()
                .created_by(username)
                .created_date(now)
                .delete_check('N')
                .member(member)
                .board(board)
                .build();
        boardCommentDto = boardCommentDtoSet;

//        boardCommentDto.setCreated_by(username);
//        boardCommentDto.setCreated_date(now);
//        boardCommentDto.setDelete_check('N');
//        boardCommentDto.setMember(member);
//        boardCommentDto.setBoard(board);

        boardCommentService.saveBoardComment(boardCommentDto);

        List<BoardComment> comments = boardCommentRepository.findCommentsBoardId(id);

        model.addAttribute("comments", comments);
        model.addAttribute(board);
        return "/board/boardContent";
    }
}
