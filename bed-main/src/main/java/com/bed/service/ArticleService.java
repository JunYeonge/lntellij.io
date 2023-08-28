package com.bed.service;

import com.bed.dto.ArticleDto;
import com.bed.dto.ArticleSearchDto;
import com.bed.entity.Article;
import com.bed.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    public List<Article> index(){
        return articleRepository.findAll();
    }
    public Article show(Long id){
        return articleRepository.findById(id).orElse(null);
    }
    public Article create(ArticleDto dto){
        Article article = Article.createArticle(dto);
        if(article.getId()!=null){
            return null;
        }
        return articleRepository.save(article);
    }
    public Article update(Long id,ArticleDto dto){
        Article article = Article.createArticle(dto);
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null||id!=article.getId()){
            return null;
        }
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }
    public Article delete(Long id){
        Article target = articleRepository.findById(id).orElse(null);
        if(target==null){
            return null;
        }
        articleRepository.delete(target);
        return target;
    }
    @Transactional(readOnly = true)
    public Page<Article> getArticlePage(ArticleSearchDto articleSearchDto, Pageable pageable){
        return articleRepository.getArticlePage(articleSearchDto,pageable);
    }

    public void deleteArticles(List<Long> selectedArticles) {
        List<Article> articles = articleRepository.findAllById(selectedArticles);
        articleRepository.deleteAll(articles);
    }
}
