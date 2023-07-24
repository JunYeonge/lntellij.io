package hellospring.hello;

import hellospring.hello.repository.JdbcMemberRepository;
import hellospring.hello.repository.JpaMemberRepository;
import hellospring.hello.repository.MemberRepository;
import hellospring.hello.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
//    private final DataSource dataSource;
//    private final EntityManager em;
//    public SpringConfig(DataSource dataSource, EntityManager em){
//        this.dataSource = dataSource;
//        this.em = em;
//    }



    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
//    @Bean
//    public MemberRepository memberRepository() {
//        return new JpaMemberRepository(em);
        //return new JdbcMemberRepository(dataSource);
        //return new MemoryMemberRepository();
//    }
    // 스프링 데이터 JPA 가 SpringDataJpaMemberRepository
    // 스프링 빈으로 자동 등록해 준다.
}
