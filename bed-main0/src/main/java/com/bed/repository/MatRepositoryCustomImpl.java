package com.bed.repository;

import com.bed.constant.*;
import com.bed.dto.ArticleSearchDto;
import com.bed.dto.MatSearchDto;
import com.bed.entity.Article;
import com.bed.entity.Mat;
import com.bed.entity.QArticle;
import com.bed.entity.QMat;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;
import java.util.List;

public class MatRepositoryCustomImpl implements MatRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;

    private JPAQueryFactory queryFactory;
    public MatRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    //성별 조건
    private BooleanExpression searchGenderEq(Gender searchGender){
        return searchGender == null ? null : QMat.mat.gender.eq(searchGender);
    }
    //연령 조건
    private BooleanExpression searchAgeEq(Age searchAge) {
        return searchAge == null ? null : QMat.mat.age.eq(searchAge);
    }
    //신장 조건
    private BooleanExpression searchHeightEq(Height searchHeight) {
        return searchHeight == null ? null : QMat.mat.height.eq(searchHeight);
    }
    //체중 조건
    private BooleanExpression searchWeightEq(Weight searchWeight) {
        return searchWeight == null ? null : QMat.mat.weight.eq(searchWeight);
    }
    //오버라이드

    @Override
    public List<Mat> getAdminMat(MatSearchDto matSearchDto) {
        BooleanExpression predicate = searchAgeEq(matSearchDto.getAge())
                .and(searchGenderEq(matSearchDto.getGender()))
                .and(searchWeightEq(matSearchDto.getWeight()))
                .and(searchHeightEq(matSearchDto.getHeight()));

        List<Mat> content = queryFactory
                .selectFrom(QMat.mat)
                .where(predicate)
                .orderBy(QMat.mat.id.desc())
                .fetch();

        return content;
    }


}
