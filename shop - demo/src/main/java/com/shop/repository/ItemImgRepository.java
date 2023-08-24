package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);
    // 상품 이미지 아이디의 오름차순위로 가져오는 쿼리 메소드
    ItemImg findByItemIdAndRepImgYn(Long itemId, String repImgYn);
    // findBy 뒤에 조건을 붙이면, 이를 해석하여 데이터베이스 조회조건을 자동 생성
    // itemId : 조회할 ItemImg 엔티티의 itemId 값입니다.
    // repImgYn : 조회할 ItemImg 엔티티의 repImgYn 값입니다.
}
