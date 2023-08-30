package com.bed.repository;

import com.bed.dto.ArticleSearchDto;
import com.bed.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    Page<Article> getArticlePage(ArticleSearchDto articleSearchDto, Pageable pageable);
}
