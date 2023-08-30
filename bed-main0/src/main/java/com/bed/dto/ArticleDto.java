package com.bed.dto;

import com.bed.constant.ArticleType;
import com.bed.entity.Article;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ArticleDto {
    private Long id;
    @NotEmpty(message = "제목은 필수 입력값입니다.")
    private String title;
    @NotEmpty(message = "내용은 필수 입력값입니다.")
    private String content;
    private ArticleType articleType;


    public Article toEntity(){
        return new Article(id,title,content,articleType);
    }
}
