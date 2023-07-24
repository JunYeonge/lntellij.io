package hellospring.hello.service;

import hellospring.hello.domain.Member;
import hellospring.hello.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    //테스트는 given when then 문법을 추천한다
    //
    //상황이 주어져서 /실행 했을때 / 결과 로 나누어진다
    MemoryMemberRepository memberRepository = new MemoryMemberRepository();

    MemberService memberService = new MemberService(memberRepository);

    @AfterEach
    public void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void join() {
        //given
        Member member = new Member();
        member.setName("Spring");
        //when
        Long saveId = memberService.join(member);
        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

        System.out.println("member: "+member.getName());
        System.out.println("findMember: "+ findMember.getName());
    }

    @Test
    public void  중복회원인경우 (){
        Member member1 = new Member();
        member1.setName("Spring1");

        Member member2 = new Member();
        member2.setName("Spring1");
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        // assertThrows 메서드는 두개의 매개변수를 가지는데
        // 첫번째 매개변수 예상되는 예외클래스타임 (IllegalStateException) 이고
        // 두 번째 매개변수는 예외가 발생할 코드 블록(() -> memberService.join(member2))입니다.
       Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

//        try{
//            memberService.join(member2);
////          fail("예외가 발생해야한다.");
//        }catch(IllegalStateException e){
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//        }
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}