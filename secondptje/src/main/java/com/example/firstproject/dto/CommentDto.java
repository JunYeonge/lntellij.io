package com.example.firstproject.dto;

import com.example.firstproject.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class CommentDto {
    private Long id;
    @JsonProperty("article_id")
    private Long articleId;
    private String nickname;
    private String body;
    //articleId 속성을 article_id로 매핑
    // 클라이언트가 "article_id" 로 댓글을 전송 할 떄
    // 서버에서 이 값을 articleId 속성에 자동으로 매핑하게 됩니다.

    public static CommentDto createCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getArticle().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
    //Comment 객체를 기반으로 새로운 CommentDto 객체를 생성
    //Comment 객체를 매개변수로 받으며, 그 객체의 속성
    // (id, articleId, nickname, body)을 사용하여 CommentDto 객체를 생성
}
