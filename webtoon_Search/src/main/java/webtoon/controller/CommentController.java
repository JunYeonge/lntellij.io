package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import webtoon.dto.BoardDto;
import webtoon.dto.CommentDto;
import webtoon.entity.board.BoardComment;
import webtoon.service.BoardService;
import webtoon.service.CommentService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/list/{id}")
    public ResponseEntity save(@ModelAttribute CommentDto commentDto) {
        Long saveResult = commentService.save(commentDto);
        if (saveResult != null) {
            List<CommentDto> commentDTOList = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("해당 게시글이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

}

