package hellospring.hello.repository;

import hellospring.hello.domain.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends CrudRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);
}

//스프링 데이터 JPA
// 스프링 데이터 JPA 를 사용하면, 리포지토리에 구현 클래스 없이
// 인터페이스 만으로 개발을 완료 할 수 있다. 그리고 반복 개발해온 기몬 CRUD 기능도
// 스프링 데이터 JPA 가 모두 제공한다.

// 스프링 데이터 JPA 제공 기능
// 인터페이스를 통한 기본적인 CRUD
//findByName(), findByEmall() 처럼 메소드 이름만으로 조회 기능 제공
// 페이징 기능 자동 제공

//TIP
// 실무에서는 JPA와 스프링 데이터 JPA를 기본적으로 사용하고,
// 복잡한 동적쿼리는 Querydsl 이라는 라이브러리를 사용하면 된다
// Querydsl을 사용하면 쿼리도 자바 코드로 안전하게 작성할 수 있고, 동적 쿼리도 편리하게 작성할 수 있다.
// 이 조합으로 해결하기 어려운 쿼리는 JPA가 제공하는 네이티브 쿼리를 사용하거나, JdbcTemplate를 사용하면 된다.