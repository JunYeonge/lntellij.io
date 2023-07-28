package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        //예상
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));
        //실제
        List<Article> articles = articleService.index();
        //검증
        assertEquals(expected.toString(),articles.toString());

//        assertEquals(expected.size(),articles.size());
//        for (int i = 0; i < expected.size(); i++) {
//            assertEquals(expected.get(i),articles.get(i));
        }


    @Test
    void show_성공() {
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        //실제
        Article article = articleService.show(id);
        //비교
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패() {
        Long id = -1L;
        // 테스트의 사용할 id 값으로 -1L 이 사용 - 이 값은 존재하지 않는 article id값으로 설정
        Article expected = null; //예상되는 결과로  null 이 설정
        //실제
        // articleService.show(id); 로 조회된 article 이 존재하지 않아 null 을 반환
        Article article = articleService.show(id);
        //비교
        assertEquals(expected, article);
        // 기대하는 값도 null 이고 articleService 에서 해당 id 로 조회된 Article 이 존재
        // 하지 않아 null 반환하므로 테스트는 성공
    }

    @Test
    void create_성공() {
        //예상
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null,title, content);
        Article expected = new Article(4L, title, content);

        //실제
        Article article = articleService.create(dto);
        //비교
        assertEquals(expected.toString(),article.toString());
        System.out.println("기대 : " + expected.toString());
        System.out.println("실제 : " + article.toString());
    }
    @Test
    void create_실패() {
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(4L,title, content);
        Article expected = null;

        //실제
        Article article = articleService.create(dto);

        //비교
        assertEquals(expected,article);
    }
}