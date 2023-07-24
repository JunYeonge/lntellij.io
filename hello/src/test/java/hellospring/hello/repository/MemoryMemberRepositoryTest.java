package hellospring.hello.repository;

import hellospring.hello.domain.Member;
//import org.junit.jupiter.api.Assertions;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class MemoryMemberRepositoryTest {
    MemoryMemberRepository repository = new MemoryMemberRepository();

@AfterEach
public void afterEach(){
    repository.clearStore();
}
//각각 테스트가 끝나면 메모리를 비워준다.


    @Test
    void save() {
        Member member = new Member();
        member.setName("spring");
        repository.save(member);

        Member result = repository.findById(member.getId()).get();
        //optional 에서 id를 꺼낼때는 get()을 사용한다.
        //Assertions.assertEquals(member , result);
        assertThat(member).isEqualTo(result);
        System.out.println("Member: " + member);
        System.out.println("result: " + result);

    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member ();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring2").get();

        assertThat(result).isEqualTo(member2);
    }

    @Test
    void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member ();
        member2.setName("spring2");
        repository.save(member2);
        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
        //전체 목록의 갯수가 총 2개인지 확인
    }
}