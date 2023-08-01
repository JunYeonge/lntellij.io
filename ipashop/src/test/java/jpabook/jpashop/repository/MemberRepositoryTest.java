//package jpabook.jpashop.repository;
//
//import jpabook.jpashop.domain.Member;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.transaction.annotation.Transactional;
//
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class MemberRepositoryTest {
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    @Transactional
//    @Rollback(false)
//    void save() {
//        Member member = new Member();
//        member.setName("memberA");
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
//        System.out.println(findMember.getName());
//        System.out.println(member.getName());
//    }
//
//    @Test
//    void find() {
//    }
//}