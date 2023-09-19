package webtoon.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webtoon.dto.board.CommentDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;
import webtoon.service.board.CommentService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommentController {

    private final CommentService commentService;
    private final MemberRepository memberRepository;

    @PostMapping("/list/{id}")
    public String save(@ModelAttribute("commentDto") @Valid CommentDto commentDto,
                       @PathVariable Long id,
                       Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Long boardId = id;

        Member member = memberRepository.findByEmail(username);
        String nickname = member.getNickname();

        // 댓글 내용이 빈 문자열인 경우
        if (commentDto.getCommentContent() == null || commentDto.getCommentContent().trim().isEmpty()) {
            // 에러 메시지를 모델에 추가
            model.addAttribute("error", "댓글 내용을 입력하세요.");
        }else {
            commentDto.setId(boardId);
            commentDto.setCommentWriter(nickname);
            commentService.save(commentDto);
        }

        // 댓글 목록을 다시 불러오기
        List<CommentDto> commentDTOList = commentService.findAll(boardId);

        // 댓글 목록을 모델에 추가
        model.addAttribute("commentList", commentDTOList);

        return "redirect:/board/list/{id}";
    }

}