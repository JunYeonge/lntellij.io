package hellospring.hello.repository;

import hellospring.hello.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository{

    private final EntityManager em;
    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }
    //EntityManager 는 JPA 의 핵심 인터페이스중 하나 영속성 컨텍스트를 관리
    // 하고,  데이터 베이스와 상호작용

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name= :name",
                        Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m",Member.class)
                .getResultList();
        //Member 를 엔티티객체를 대상으로 조회하고, 결과를 Member 클래스의 객체로
        //맵핑합니다.
    }
}
//Member를 엔티티객체를 대상으로 조회하고, 결과를 Member 클래스의 객체로 맵핑합니다
//데이터베이스와 연동할 때 , 엔티티는 일반적으로 테이블 과 매핑됩니다.
// 각 엔티티의 속성은 테이블의 컬럼(필드)으로 매칭되고, 객체 간의 관계는 테이블 간의 관계로 매칭됩니다.

//ORM(object Relational Mapping) 기술을 사용하면, 엔티티와 데이터베이스 간의 변환 작업을
//자동화하여 개발자가 직접 SQL을 작성하지 않고도 데이터 베이스와 상호 작용할수 있게 됩니다.
//JAVA에서는 JPA(Java Persitence API)가 주로 사용되며, 이를 통해서 엔티티 클래스를 정의하고
//데이터베이스와의 관계를 설정하여 데이터를 영구적으로 관리할 수 있습니다.