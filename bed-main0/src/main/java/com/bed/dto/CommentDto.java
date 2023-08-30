package com.bed.dto;

import com.bed.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
public class CommentDto {
    @JsonProperty("comment_id")
    private Long id;

    @JsonProperty("as_form_id")
    private Long asFormId;
    private String nickname;
    private String body;
    public static CommentDto createCommentDto(Comment comment){
        return new CommentDto(
                comment.getId(),
                comment.getAsForm().getId(),
                comment.getNickname(),
                comment.getBody()
        );
    }
}
