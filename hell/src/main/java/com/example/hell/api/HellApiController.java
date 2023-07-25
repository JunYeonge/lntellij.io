package com.example.hell.api;

import com.example.hell.dto.ArticleForm;
import com.example.hell.entity.Article;
import com.example.hell.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
public class HellApiController {

    @Autowired
    private ArticleRepository  articleRepository;

    @GetMapping("/api/hello")
    public String hello() {
        return "hello world";
    }

    @GetMapping("/api/articles")
    public List<Article> index() {
        return articleRepository.findAll();
    }

    //값이 여러개중 1개의 값을 찾을때
    //@PathVariable는 GetMapping("/api/articles/{id}") 처럼 주소에 변수가 있을경우 변수의 값을 추출하여
    //서버에게 전달한 변수의 값을 메서드에서 활용 할 수 있게 해준다.
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/articles/{id}")
    public ResponseEntity<Article> update (@PathVariable Long id,
                                           @RequestBody ArticleForm dto) {

        // dto 에서 엔티티로 변경
        Article article = dto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());

        // 타겟을 조회
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if (target == null || article.getId() != id) {
            log.info("잘못된 요청 id : {} , article : {}", id,article.toString());
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(article);
        Article update = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
