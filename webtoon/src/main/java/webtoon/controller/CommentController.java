package webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webtoon.dto.CommentDto;
import webtoon.entity.board.BoardComment;
import webtoon.service.CommentService;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/board/list/{id}")
    public String addComment(@PathVariable Long id, @RequestParam("content") String content) {
        try {
            CommentDto commentDto = commentService.createCommentDto(content);
            commentService.addComment(id, commentDto);
        } catch (Exception e) {
            // 오류 처리 로직을 추가하거나 로깅합니다.
            e.printStackTrace();
        }
        return "redirect:/board/list/{id}";// 게시물 상세 페이지로 리다이렉트
    }
}

