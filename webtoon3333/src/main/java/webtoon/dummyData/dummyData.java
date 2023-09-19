//package webtoon.dummyData;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.stereotype.Component;
//import webtoon.constant.Role;
//import webtoon.entity.member.Member;
//import webtoon.entity.webtoon.Webtoon;
//import webtoon.repository.member.MemberRepository;
//import webtoon.repository.webtoon.WebtoonRepository;
//
//
//@Component
//@SpringBootApplication
//public class dummyData implements CommandLineRunner {
//
//    private final MemberRepository memberRepository;
//    private final WebtoonRepository webtoonRepository;
//
//    @Autowired
//    public dummyData(MemberRepository memberRepository, WebtoonRepository webtoonRepository) {
//        this.memberRepository = memberRepository;
//        this.webtoonRepository = webtoonRepository;
//    }
//    @Override
//    public void run(String... args) throws Exception {
//        Member member = memberRepository.findById(8l).orElse(null);
//                memberRepository.delete(member);
//
//              // 다대일 관계 및 삭제 테스트 코드
////        Webtoon deletewebtoon = webtoonRepository.findById(9l)
////                .orElse(null);
////        webtoonRepository.delete(deletewebtoon);
//        // 더미 데이터 생성 및 저장 예시
//        Member member1 = new Member();
//        member1.setUser_id("user1");
//        member1.setPassword("00000000");
//        member1.setName("John Doe");
//        member1.setEmail("john@example.com");
//        member1.setAddress("123 Main St, City");
//        member1.setNickname("johndoe");
//        member1.setPoint(100);
//        member1.setAge(30);
//        member1.setGender("Male");
//        member1.setRole(Role.ADMIN);
//        memberRepository.save(member1);
//
//        Member member2 = new Member();
//        member2.setUser_id("user2");
//        member2.setPassword("00000000");
//        member2.setName("Jane Smith");
//        member2.setEmail("jane@example.com");
//        member2.setAddress("456 Elm St, Town");
//        member2.setNickname("janesmith");
//        member2.setPoint(200);
//        member2.setAge(25);
//        member2.setGender("Female");
//        member2.setRole(Role.ADMIN);
//        memberRepository.save(member2);
//
//        Member member3 = new Member();
//        member3.setUser_id("user3");
//        member3.setPassword("00000000");
//        member3.setName("Bob Johnson");
//        member3.setEmail("bob@example.com");
//        member3.setAddress("789 Oak St, Village");
//        member3.setNickname("bobjohnson");
//        member3.setPoint(150);
//        member3.setAge(40);
//        member3.setGender("Male");
//        member3.setRole(Role.ADMIN);
//        memberRepository.save(member3);
//
//    }
//
//}
