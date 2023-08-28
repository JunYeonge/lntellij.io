package com.bed.repository;

import com.bed.dto.ArticleSearchDto;
import com.bed.dto.MemberSearchDto;
import com.bed.entity.Article;
import com.bed.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
    Page<Member> getMemberPage(MemberSearchDto memberSearchDto, Pageable pageable);
}
