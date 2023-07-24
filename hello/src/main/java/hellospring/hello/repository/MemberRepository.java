package hellospring.hello.repository;

import hellospring.hello.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);  //맴버객체를 저장
    Optional<Member> findById(Long id);//주어진 id에 해당하는 멤버객체를 찾아 반환
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
