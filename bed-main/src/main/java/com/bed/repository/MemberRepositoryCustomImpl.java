package com.bed.repository;

import com.bed.constant.ArticleType;
import com.bed.constant.Role;
import com.bed.dto.ArticleSearchDto;
import com.bed.dto.MemberSearchDto;
import com.bed.entity.Article;
import com.bed.entity.Member;
import com.bed.entity.QArticle;
import com.bed.entity.QMember;
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

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{
    private JPAQueryFactory queryFactory;
    public MemberRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }
//    private BooleanExpression searchProTypeEq(String searchProType) {
//
////        if (searchProType.equalsIgnoreCase("email")) {
////            return QMember.member.provider.isNull();
////        } else
//        if (searchProType.equalsIgnoreCase("google")) {
//            return QMember.member.provider.equalsIgnoreCase("google");
//        }else if (searchProType.equalsIgnoreCase("kakao")) {
//            return QMember.member.provider.equalsIgnoreCase("kakao");
//        }else if (searchProType.equalsIgnoreCase("naver")) {
//            return QMember.member.provider.equalsIgnoreCase("naver");
//        }
//
//        return null;
//    }


    private BooleanExpression searchRoleTypeEq(Role searchRoleType){
        return searchRoleType == null ? null : QMember.member.role.eq(searchRoleType);
    }
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
        return QMember.member.regTime.after(dateTime);
    }
    private BooleanExpression searchByLike(String searchBy,String searchQuery){
        if(StringUtils.equals("email",searchBy)){
            return QMember.member.email.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("name",searchBy)){
            return QMember.member.name.like("%"+searchQuery+"%");
        }else if(StringUtils.equals("phoneNumber", searchBy)){
            return QMember.member.phoneNumber.like("%"+searchQuery+"%");
        }
        return null;
    }
    @Override
    public Page<Member> getMemberPage(MemberSearchDto memberSearchDto, Pageable pageable){
        List<Member> content = queryFactory
                .selectFrom(QMember.member)
                .where(regDtsAfter(memberSearchDto.getSearchDateType()),
                        searchRoleTypeEq(memberSearchDto.getSearchRoleType()),
                                searchByLike(memberSearchDto.getSearchBy(),
                                        memberSearchDto.getSearchQuery()))
                                .orderBy(QMember.member.id.desc())
                                .offset(pageable.getOffset())
                                .limit(pageable.getPageSize())
                                .fetch();
        long total = queryFactory.select(Wildcard.count).from(QMember.member)
                .where(regDtsAfter(memberSearchDto.getSearchDateType()),
//                        searchProTypeEq(memberSearchDto.getSearchProType()),
                        searchRoleTypeEq(memberSearchDto.getSearchRoleType()),
                        searchByLike(memberSearchDto.getSearchBy(), memberSearchDto.getSearchQuery()))
                .fetchOne();
        return new PageImpl<>(content, pageable, total);
    }

}
