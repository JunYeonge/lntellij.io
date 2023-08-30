package com.bed.controller;

import com.bed.dto.CommentDto;
import com.bed.entity.Comment;
import com.bed.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    //댓글조회
    @GetMapping("/api/asSubmit/{asFormId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long asFormId){
        List<CommentDto> dtos = commentService.comments(asFormId);
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }
    //댓글생성
    @PostMapping("/api/asSubmit/{asFormId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long asFormId, @RequestBody CommentDto dto){
        CommentDto createdDto = commentService.create(asFormId,dto);
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }
    //댓글수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id,@RequestBody CommentDto dto){
        CommentDto updatedDto = commentService.update(id,dto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    //댓글삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        CommentDto deletedDto = commentService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
