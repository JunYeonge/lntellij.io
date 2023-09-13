package webtoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import webtoon.entity.board.BoardComment;
import webtoon.service.CommentService;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/board/lst/{id}")
    public String addComment(@PathVariable Long boardId, BoardComment boardComment) {
        commentService.addComment(boardId, boardComment);
        return "redirect:/board/list/{id}"; // 게시물 상세 페이지로 리다이렉트
    }
}
