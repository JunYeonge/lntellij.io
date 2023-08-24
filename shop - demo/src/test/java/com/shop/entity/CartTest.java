package com.shop.entity;

import com.shop.dto.MemberFormDto;
import com.shop.repository.CartRepository;
import com.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    public Member createMember(){
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail("test@email.com");
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword("1234");
        return Member.createMember(memberFormDto, passwordEncoder);
    }


    @Test
    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
    public void fineCartAndMemberTest() {
        Member member = createMember(); // createMember() 호출해서 member 객체를 생성
        memberRepository.save(member); //회원 객체를 회원 저장소 저장
        Cart cart = new Cart(); // cart 객체 생성
        cart.setMember(member); // 생성한 회원객체를 cart 객체에 연결(장바구니와 회원간의 연관관계)
        cartRepository.save(cart);//연관관계가 설정된 cart 객체를 장바구니 저장소에 저장

        em.flush(); //변경된 내용을 데이터베이스에 즉시 반영
        em.clear(); // 영속성 컨텍스트 초기화

       Cart savedCart = cartRepository.findById(cart.getId())
               .orElseThrow(EntityNotFoundException::new); 
       //저장한 cart 객체를 데이버베이스에서 조회
       assertEquals(savedCart.getMember().getId(), member.getId());
        // 조회한 cart 객체의 회원id와 원래 회원 객체의  id를 비교테스트
    }
}
// 장바구니와 회원간의 연관관계가 제대로 설정 되어 있는지를 확인
// 즉 회원이 생성되고, 해당회원과 연관된 장바구니가 생성되고, 그 연관관계가 정확하게
// 유지되는 지를 테스트