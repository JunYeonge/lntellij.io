package com.bed.repository;

import com.bed.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.ArrayList;

public interface ArticleRepository extends JpaRepository<Article, Long>, QuerydslPredicateExecutor<Article>,ArticleRepositoryCustom {
    @Override
    ArrayList<Article> findAll();
}
