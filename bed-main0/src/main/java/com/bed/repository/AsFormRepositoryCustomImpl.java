package com.bed.repository;

import com.bed.dto.ArticleSearchDto;
import com.bed.dto.AsFormSearchDto;
import com.bed.entity.*;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

public class AsFormRepositoryCustomImpl implements AsFormRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public AsFormRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
    //기간 조건
    private BooleanExpression regDtsAfter(String searchDateType){
        LocalDateTime dateTime = LocalDateTime.now();
        if(StringUtils.equals("all", searchDateType) || searchDateType == null){
            return null;
        } else if(StringUtils.equals("1d", searchDateType)){
            dateTime = dateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)){
            dateTime = dateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)){
            dateTime = dateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)){
            dateTime = dateTime.minusMonths(6);
        }
        return QAsForm.asForm.regTime.after(dateTime);
    }
    //검색어 조건
    private BooleanExpression searchByLike(String searchBy,String searchQuery){
        if(StringUtils.equals("email",searchBy)){
            return QAsForm.asForm.email.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("tel",searchBy)){
            return QAsForm.asForm.tel.like("%"+searchQuery+"%");
        }
        return null;
    }
    @Override
    public Page<AsForm> getAdminAsPage(AsFormSearchDto asFormSearchDto, Pageable pageable){
        List<AsForm> content = queryFactory
                .selectFrom(QAsForm.asForm)
                .where(regDtsAfter(asFormSearchDto.getSearchDateType()),
                        searchByLike(asFormSearchDto.getSearchBy(),
                                asFormSearchDto.getSearchQuery()))
                .orderBy(QAsForm.asForm.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QAsForm.asForm)
                .where(regDtsAfter(asFormSearchDto.getSearchDateType()),
                        searchByLike(asFormSearchDto.getSearchBy(), asFormSearchDto.getSearchQuery()))
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }
}
