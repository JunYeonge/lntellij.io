//package toyproject.board.domain.service;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Commit;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import toyproject.board.domain.Member;
//import toyproject.board.repository.MemberRepository;
//import toyproject.board.service.MemberService;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@RunWith(SpringRunner.class)
//@Transactional
//@SpringBootTest
//class MemberServiceTest {
//
//    @Autowired
//    MemberService memberService;
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    void 회원_가입() {
//        //given
//        Member member = Member.builder()
//                .username("mins")
//                .password("1234")
//                .email("email")
//                .build();
//        //when
//        Long joinId = memberService.join(member);
//        //then
//        Assertions.assertEquals(member, memberRepository.findOne(joinId));
//    }
//
//    @Test
//    @Rollback
//    void 중복_회원_가입() {
//        //given
//        Member member = Member.builder()
//                .username("min")
//                .password("1234")
//                .email("email")
//                .build();
//
//        Member member2 = Member.builder()
//                .username("min")
//                .password("1234")
//                .email("email")
//                .build();
//        //when
//        memberService.join(member);
//        //then
//        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
//    }
//
//    @Test
//    @Commit
//    void 생성시간_엔티티_등록() {
//        //given
//        LocalDateTime now = LocalDateTime.now();
//        memberService.join(Member.builder()
//                .username("minhwan")
//                .email("ajsl@naver.com")
//                .password("12345")
//                .build());
//        //when
//        List<Member> allMember = memberRepository.findAll();
//        //then
//        Member members = allMember.get(0);
//        Assertions.assertTrue(members.getCreatedDate().isAfter(now));
//
//    }
//
//}