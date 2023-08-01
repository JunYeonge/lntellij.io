package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    public void 화원가입() throws Exception {
        //Given - 테스트를 준비하기 위한 초기 상태
        //테스트를 실행하기 위해 필요한 데이터나 객체등 준비작업


        //When - 실제 테스트한 동작 수행
        //테스트 하고자하는 특정 동작이나 메서드 호출 등을 실행

        //Then - 테스트 결과 확인
        //예상결과와 실제 결과를 비교하여 테스트를 성공여부 판단

        //Given
        Member member = new Member();
        member.setName("kim");

        //When
        Long saveId = memberService.join(member);
        //Member expect = memberRepository.findOne(saveId);

        //Then
        //assertEquals(member.toString(), expect.toString());
        //System.out.println(member.toString());
        //System.out.println(expect.toString());
        assertEquals(member,memberRepository.findOne(saveId));

    }

    @Test
    public void 중복_회원_예외() throws Exception{
        //Given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        //When
        memberService.join(member1);
        //memberService.join(member2); // 예외가 발생해야한다.

        //Then
        //fail("예외가 발생해야한다.");
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    //IllegalArgumentException - 메서드에 전달된 인자가 유효하지않은 경우 발생하는 예외
    //IllegalStateException - 메서드 호출이 잘못된 상태에서 수행될때 발생하는 예외
    //DuplicateKeyException - 데이터 베이스나 저장속에 중복된 키로 데이터를 삽입하고자 할 떄

    @Test
    void findOne() {
    }
}