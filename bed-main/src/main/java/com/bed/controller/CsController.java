package com.bed.controller;

import com.bed.dto.*;
import com.bed.entity.Article;
import com.bed.entity.AsForm;
import com.bed.entity.Member;
import com.bed.repository.ArticleRepository;
import com.bed.repository.AsFormRepository;
import com.bed.service.ArticleService;
import com.bed.service.AsFormService;
import com.bed.service.CommentService;
import com.bed.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.io.File;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class CsController {
    private final AsFormService asFormService;
    private final AsFormRepository asFormRepository;
    private final ArticleService articleService;
    private final ArticleRepository articleRepository;
    private final CommentService commentService;
    private final MemberService memberService;

//자주묻는질문 목록
    @GetMapping(value = "/CsCenter/faq")
    public String faq(Model model) {
        model.addAttribute("headerImg","/images/header.jpg");
        return "cscenter/faq";
    }
//as 신청 페이지
    @GetMapping(value = "/CsCenter/asSubmit")
    public String asSubmit(Model model) {
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("asFormDto", new AsFormDto());

        return "cscenter/asSubmit";
    }
//as 신청 폼 전송
    @PostMapping("/CsCenter/asSubmit")
    public String submitAsForm(@ModelAttribute @Valid AsFormDto asFormDto, Model model,
                               @RequestParam("asFormImgFile")MultipartFile file) {
//        if(bindingResult.hasErrors()){
//            model.addAttribute("errorMessage", "유효성 검사 오류가 발생하였습니다.");
//            return "cscenter/asSubmit";
//        }
        try {
            if (file != null && !file.isEmpty()) {
                // 파일이 선택된 경우
                AsForm asForm = AsForm.fromDto(asFormDto);
                asFormService.saveAs(asForm,file);
//                asFormService.saveAsFormFile(asFormDto, file);

            } else {
                // 파일이 선택되지 않은 경우
                AsForm asForm = AsForm.fromDto(asFormDto);
                asFormService.saveAsForm(asForm);
            }

        }catch (Exception e){
            model.addAttribute("errorMessage","AS 신청서 전송 중 에러가 발생하였습니다.");
            return "cscenter/asSubmit";
        }
        return "redirect:/";
    }
//관리자 페이지에서 as 신청내용 목록 확인하기
    @GetMapping(value = {"/admin/asSubmits","/admin/asSubmits/{page}"})
    public String adminAsForm(AsFormSearchDto asFormSearchDto,@PathVariable("page") Optional<Integer> page, Model model){
        model.addAttribute("headerImg","/images/header.jpg");
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<AsForm> asForms = asFormService.getAdminAsPage(asFormSearchDto,pageable);
        model.addAttribute("asForms", asForms);
        model.addAttribute("asFormSearchDto",asFormSearchDto);
        model.addAttribute("maxPage",5);
        return "articles/adminAs";
    }
//관리자 페이지에서 as 신청 내용 상세페이지 확인하기 -** 내용 보완 요망
    @GetMapping("/admin/asSubmit/{id}")
    public String adminAsShow(@PathVariable("id") Long id, Model model){
        AsForm asForm = asFormRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
//        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("asForm",asForm);
        model.addAttribute("createdBy", asForm.getCreatedBy());
        model.addAttribute("commentDtos",commentsDtos);
        return "articles/adminAsShow";
    }
//관리자페이지에서 as 신청 폼 삭제하기
    @GetMapping("/admin/asSubmit/{id}/delete")
    public String deleteAs(@PathVariable Long id, RedirectAttributes rttr){
        AsForm target = asFormRepository.findById(id).orElse(null);
        if(target!=null){
            asFormRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다.");
        }
        return "redirect:/admin/asSubmits";
    }
//관리자페이지에서 as신청확인 체크하기
    @GetMapping("/admin/asSubmit/{id}/check")
    public String checkAs(@PathVariable("id")Long id){
        asFormService.checkAs(id);
        return "redirect:/admin/asSubmits";
    }
//관리자페이지에서 as신청 폼 삭제하기
    @PostMapping("/admin/asSubmits/delete")
    public String deleteSelectedAS(@RequestParam("selectedForms") List<Long> selectedForms) {
        // 선택된 게시물 삭제
        asFormService.deleteAs(selectedForms);

        // 삭제 후 리다이렉트할 페이지로 이동
        return "redirect:/admin/asSubmits";
    }


//공지사항 게시판
    @GetMapping(value = {"/CsCenter/notices","/CsCenter/notices/{page}"})
    public String notice(ArticleSearchDto articleSearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        model.addAttribute("headerImg","/images/header.jpg");
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);
        Page<Article> articles = articleService.getArticlePage(articleSearchDto,pageable);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("articleList", articles);
        model.addAttribute("articleSearchDto",articleSearchDto);
        model.addAttribute("maxPage",5);
        return "cscenter/notice";
    }
//공지사항 상세 페이지
    @GetMapping("/CsCenter/notice/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("article",articleEntity);
        model.addAttribute("createdBy", articleEntity.getCreatedBy());
        model.addAttribute("commentDtos",commentsDtos);
        return "articles/show";
    }
//관리자 공지사항 게시판
    @GetMapping(value = {"/admin/notices", "/admin/notices/{page}"})
    public String adminNotice(ArticleSearchDto articleSearchDto, @PathVariable("page") Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);
        Page<Article> articles = articleService.getArticlePage(articleSearchDto,pageable);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("articleList", articles);
        model.addAttribute("articleSearchDto",articleSearchDto);
        model.addAttribute("maxPage",5);
        return "articles/adminArticleList";
    }
//관리자페이지에서 게시글 상세 확인
    @GetMapping("/admin/notice/{id}")
    public String adminShow(@PathVariable("id") Long id, Model model){
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("article",articleEntity);
        model.addAttribute("createdBy", articleEntity.getCreatedBy());
        model.addAttribute("commentDtos",commentsDtos);
        return "articles/adminShow";
    }
//관리자페이지에서 게시글 수정
    @GetMapping("/admin/notice/{id}/edit")
    public String edit(@PathVariable Long id,Model model){
        Article articleEdit = articleRepository.findById(id).orElse(null);
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("article",articleEdit);
        return "articles/edit";
    }
//관리자페이지에서 공지 작성 페이지
    @GetMapping("/admin/notice/create")
    public String newArticleForm(Model model){
        model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("ArticleDto",new ArticleDto());
        return "articles/new";
    }
//관리자페이지에서 공지 작성 폼 전송
    @PostMapping("/admin/notice/create")
    public String createArticle(@Valid ArticleDto articleDto, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "articles/new";
        }
        try{
            Article article = Article.createArticle(articleDto);
            Article saved = articleRepository.save(article);
            return "redirect:/admin/notices";
    //            return "redirect:/articles/"+saved.getId();
        }catch(IllegalStateException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "articles/new";
        }

    }
//관리자페이지에서 공지 수정 폼 전송
    @PostMapping("/admin/notice/update")
    public String update(@Valid ArticleDto dto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "articles/adminArticleList";
        }
        try {
            Optional<Article> existingArticleOptional = articleRepository.findById(dto.getId());
            if (existingArticleOptional.isPresent()) {
                Article existingArticle = existingArticleOptional.get();
                existingArticle.setTitle(dto.getTitle());
                existingArticle.setContent(dto.getContent());
                articleRepository.save(existingArticle);
                return "redirect:/admin/notices/" + existingArticle.getId();
            } else {
                // 해당 ID의 글을 찾을 수 없는 경우에 대한 처리
                model.addAttribute("errorMessage", "해당 글을 찾을 수 없습니다.");
                return "articles/adminArticleList";
            }
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "articles/adminArticleList";
        }
    }
//관리자페이지에서 공지 삭제하기(단수 삭제)
    @GetMapping("/admin/notice/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        Article target = articleRepository.findById(id).orElse(null);
        if(target!=null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다.");
        }
        return "redirect:/admin/notices";
    }
//관리자페이지에서 공지 삭제하기(복수 삭제)
    @PostMapping("/admin/notices/delete")
    public String deleteSelectedArticles(@RequestParam("selectedArticles") List<Long> selectedArticles) {
        // 선택된 게시물 삭제
        articleService.deleteArticles(selectedArticles);

        // 삭제 후 리다이렉트할 페이지로 이동
        return "redirect:/admin/notices";
    }
//유저페이지에서 AS신청리스트 확인
    @GetMapping(value = {"/user/asSubmits", "/user/asSubmits/{page}"})
    public String userAsForm(@PathVariable("page") Optional<Integer> page, Principal principal, Model model) {
        model.addAttribute("headerImg", "/images/header.jpg");
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 4);
        Page<AsHistDto> asHistDtoList = asFormService.getAsList(principal.getName(), pageable);

        model.addAttribute("asForms", asHistDtoList);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("maxPage", 5);
        return "cscenter/myAsList";
    }
//유저페이지에서 AS신청 페이지 확인
    @GetMapping("/user/asSubmit/{id}")
    public String userAsShow(@PathVariable("id") Long id, Model model){
        AsForm asForm = asFormRepository.findById(id).orElse(null);
        List<CommentDto> commentsDtos = commentService.comments(id);
            model.addAttribute("headerImg","/images/header.jpg");
        model.addAttribute("asForm",asForm);
        model.addAttribute("createdBy", asForm.getCreatedBy());
        model.addAttribute("commentDtos",commentsDtos);
        return "cscenter/myAsShow";
    }
//유저페이지에서 AS신청 상세페이지 삭제
    @GetMapping("/user/asSubmit/{id}/delete")
    public String deleteUserAs(@PathVariable Long id, RedirectAttributes rttr){
        AsForm target = asFormRepository.findById(id).orElse(null);
        if(target!=null){
            asFormRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제되었습니다.");
        }
        return "redirect:/user/asSubmits";
    }
}
