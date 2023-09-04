package com.codestates.notice_project.board.controller;

import com.codestates.notice_project.board.dto.BoardDto;
import com.codestates.notice_project.board.entity.Board;
import com.codestates.notice_project.board.mapper.BoardMapper;
import com.codestates.notice_project.board.service.BoardService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/boards")
@Validated
public class BoardController {

    private final BoardService boardService;
    private final BoardMapper mapper;

    public BoardController(BoardService boardService, BoardMapper mapper) {
        this.boardService = boardService;
        this.mapper = mapper;
    }

    // 게시글 게시
    @PostMapping
    public ResponseEntity postBoard(@Valid @RequestBody BoardDto.Post boardPostDto){
        Board board = boardService.createBoard(mapper.boardPostDtoToBoard(boardPostDto));
        BoardDto.Response response = mapper.boardToBoardResponseDto(board);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 게시글 수정
    @PatchMapping("/{board-id}")
    public ResponseEntity patchBoard(@PathVariable("board-id") @Positive long boardId,
                                    @Valid @RequestBody BoardDto.Patch boardPatchDto){
        boardPatchDto.setBoardId(boardId);
        Board board = boardService.updateBoard(mapper.boardPatchDtoToBoard(boardPatchDto));
        BoardDto.Response response = mapper.boardToBoardResponseDto(board);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 게시글 조회
    @GetMapping("/{board-id}")
    public ResponseEntity getBoard(@PathVariable("board-id") @Positive long boardId){
        Board response = boardService.findBoard(boardId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity getBoards(@Positive @RequestParam("page") int page,
                                    @Positive @RequestParam("size") int size){
        Page<Board> boardPage = boardService.findBoards(page -1, size);
        List<Board> boardList = boardPage.getContent();
        List<BoardDto.Response> response = mapper.boardToBoardResponseDtos(boardList);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 게시글 삭제
    @DeleteMapping("/{board-id}")
    public ResponseEntity deleteBoard(@PathVariable("board-id") @Positive long boardId){
        boardService.deleteBoard(boardId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
