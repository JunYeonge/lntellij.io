package webtoon.repository.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import webtoon.entity.member.Member;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, QuerydslPredicateExecutor<Member>, MemberRepositoryCustom {
    @Override
    List<Member> findAll();

    Member findByEmail(String email);
    Member findByNickname(String nickname);

    List<Member> findAllByActiveIsFalseAndDeactivatedAtBefore(LocalDateTime threeMonthsAgo); // 3개월 전에 탈퇴한 회원 찾기

}
