package com.bed.repository;

import com.bed.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> , QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {
    @Override
    List<Member> findAll();

    Member findByEmail(String email);

    @Query(value = "select * from member m where m.email like %:keyword%", nativeQuery = true)
    List<Member> searchByKeyword(String keyword);

    Member findByPhoneNumber(String phoneNumber);

    List<Member> findAllByActiveIsFalseAndDeactivatedAtBefore(LocalDateTime threeMonthsAgo); // 3개월 전에 탈퇴한 회원 찾기


    Member findByNickname(String nickname);

    Member findByUsername(String username);



}
