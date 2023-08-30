package com.bed.repository;

import com.bed.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByMemberId(Long memberId);
    // ㄴ Cart 엔티티 에서 memberId 필드를 조회

}
