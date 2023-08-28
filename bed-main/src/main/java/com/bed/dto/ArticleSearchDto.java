package com.bed.dto;

import com.bed.constant.ArticleType;
import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class ArticleSearchDto {
    private String searchDateType;
    private ArticleType searchArticleType;
    private String searchBy; //createdBy : 등록자, title: 제목
    private String searchQuery="";
//    @Contract(pure=true)
//    public ArticleType getSearchArticleType()
}
