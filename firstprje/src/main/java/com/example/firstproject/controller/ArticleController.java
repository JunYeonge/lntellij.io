package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@Slf4j
//로깅을 위한 롬복 어노테이션
public class ArticleController {

//    private final ArticleRepository articleRepository;
//
//    @Autowired
//    public ArticleController(ArticleRepository articleRepository) {
//        this.articleRepository = articleRepository;
//    }

    @Autowired
    private ArticleRepository articleRepository;
    // 스프링 부트가 미리 생성해놓은 리파지터리 객체를 가져옴(DI)

    @Autowired
    private CommentService commentService;

    @GetMapping("articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("articles/create")
    public String createArticlesForm(ArticleForm form) {
//        System.out.println(form.toString());
//        -> println() 을 로깅으로 대체
        //from안의 내용은 dto(ArticleForm이라는 클래스 타입의 객체를 파라미터로 받는다.) 가 될것이다.

        log.info(form.toString());

        //1. dto 를 Entity 변환
        Article article= form.toEntity();
        //ArticleForm 의 객체를 article 로 변환
        //toEntity() 메소드는 ArticleForm에서 Article 객체로 데이터를 복사하여 변환
        //


//        System.out.println(article.toString());
        log.info(form.toString());

        //2. Repository 에게 Entity 를 DB로 저장
        Article saved = articleRepository.save(article);
        // Article 엔티티를 데이터베이스에 저장
//        System.out.println(saved.toString());
        log.info(form.toString());


        return "redirect:/articles/" + saved.getId();
    }

    //@PostMapping("articles/create") 다음 경로 요청이 들으면 밑에 있는 메소드로 처리
    //public String createArticlesForm(ArticleForm form)
    //ArticleForm 클래스 타입의 객체를 파라미터로 받는다.
    //System.out.println(form.toString()); ArticleForm 객체의 내용을 콘솔에 출력

    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        //url 에서 id를 변수로 가져옴
        log.info("id = " + id);

        //model - 화면에 등록을 시키기 위해서 사용한다.

        // 1 : id로 데이터를 가져옴
        Optional<Article> optionalArticle = articleRepository.findById(id);
//        Article aticleEntity = optionalArticle.orElse(null);

        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos  = commentService.comments(id);


        //findById()메서드는 Optional<T>을 반환하므로
        // 우리는 결과를 받을 Optional<Article> 변수를 선언
        // orElse() 메서드를 사용하여 Optional 객체 안의 값이 존재하면 해당 값을 반환
        // 값이 없으면 null 값을 가지게 됨


        // 2 : 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos",commentsDtos);

        // 3 : 보여줄 페이지를 설정

        return "articles/show";
    }


        @GetMapping("/articles")
            public String index(Model model) {
                // 1 : 모든 Article 을 가져온다.
            List<Article> articleEntityList = articleRepository.findAll();
            // 2 : 가져온 Article 을 묶음으로 뷰로 전달
            model.addAttribute("articleList",articleEntityList);
            // 3 : 뷰 페이지 설정

            return "articles/index";
        }

        @GetMapping("/articles/{id}/edit")
            public String edit(@PathVariable Long id,  Model model){
                //수정 할 데이터 가져오기
            Article articleEntity = articleRepository.findById(id).orElse(null);
                //모델 데이터 등록
            model.addAttribute("article",articleEntity);
                //퓨 페이지 설정
                    return "articles/edit";
        }

        @PostMapping("/articles/update")
        public String update(ArticleForm form) {
        log.info(form.toString());
        //1. dto 를 저장 할 때 엔티티로 변환
           Article articleEntity = form.toEntity();
           log.info(articleEntity.toString());
        //2. 엔티티를 DB를 저장
        //2-1 : DB 에서 기존 데이터를 가져옴
           Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2 : 기존 데이터가 있다면 , 값을 갱신
            if(target != null) {
                articleRepository.save(articleEntity);
            }
        //3. 수정 결과 페이지를 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
        }

        @GetMapping("/articles/{id}/delete")
        public String delete (@PathVariable Long id,
                              RedirectAttributes rttr) {
        // 리다이렉트로 다른 컨트롤러나 뷰로 데이터를 전달할때쓰임
        log.info("삭제 요청이 들어왔습니다.");
        //삭제대상을 가져옴
           Article target = articleRepository.findById(id).orElse(null);
           log.info(target.toString());

            // 대상을 삭제
            if(target != null){
                articleRepository.delete(target);
                rttr.addFlashAttribute("msg","삭제가 완료되었습니다.");
            }

            //결과 페이지로 리다이렉트
        return  "redirect:/articles";
        }

}
