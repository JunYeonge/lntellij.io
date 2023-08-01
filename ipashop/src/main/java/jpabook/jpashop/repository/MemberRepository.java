package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sound.midi.MetaMessage;
import java.util.List;

@Repository
@RequiredArgsConstructor //final 필드가 붙은 필드를 인자를 받는 생성자가 자동으로 생성
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);

    }
    public Member findOne(Long id) {
        return em.find(Member.class, id);

        //멤버라는 클래스를 뒤져서 id를 찾는다
        // find = 찾는 메소드
    }
//    public List<Member> findAll() {
//        TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
//        List<Member> result = query.getResultList();
//        return result;
//    }
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

//    public List<Member> findByName(String name){
//        TypedQuery<Member> query = em.createQuery("select m from Member m where m.name = :name", Member.class);
//        // 쿼리에 바인딩 변수에 값을 할당한다
//        query.setParameter("name",name);
//        List<Member> result = query.getResultList();
//        return result;
//    }

    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        //특정 이름을 가진 리스트를 조회 할 때
    }

}
