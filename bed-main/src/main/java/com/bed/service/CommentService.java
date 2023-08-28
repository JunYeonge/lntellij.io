package com.bed.service;

import com.bed.dto.CommentDto;
import com.bed.entity.Article;
import com.bed.entity.AsForm;
import com.bed.entity.Comment;
import com.bed.repository.ArticleRepository;
import com.bed.repository.AsFormRepository;
import com.bed.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final AsFormRepository asFormRepository;
    public List<CommentDto> comments(Long asFormId){
        return commentRepository.findByAsFormId(asFormId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }
    public CommentDto create(Long asFormId, CommentDto dto){
        AsForm asForm = asFormRepository.findById(asFormId)
                .orElseThrow(()->new IllegalArgumentException("댓글 실패"));
        Comment comment = Comment.createComment(dto,asForm);
        Comment created = commentRepository.save(comment);
        return CommentDto.createCommentDto(created);
    }
    public CommentDto update(Long id,CommentDto dto){
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("수정실패"));
        target.patch(dto);
        Comment updated = commentRepository.save(target);
        return CommentDto.createCommentDto(updated);
    }
    public CommentDto delete(Long id){
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("삭제실패"));
        commentRepository.delete(target);
        return CommentDto.createCommentDto(target);
    }
}
