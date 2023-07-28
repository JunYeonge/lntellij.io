package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor //생성자가 자동으로 생성

@ToString
public class ArticleForm {
    private Long id;
    private String title;
    private String content;
//    private List<Comment> comments;
//update를 위한 dto 변경 - id 필드 추가 및 엔티티 변환 메소드 변경


//    public Article toEntity() {
//        return new Article(id,title,content,comments);
//    }
    public Article toEntity() {
        return new Article(id,title,content);
    }
    //toEntity() 메소드는 새로운 Article 을 생성하는데
    // 기본인 id는 null로 설정
    // title과 content는 dto 에서 전달된다.

    // DTO - 데이터 전송매체
    // 계층틀 사이에서 데이터를 간단하고 구조화된 방식으로 캡슐화하고 전송하는 것
}
