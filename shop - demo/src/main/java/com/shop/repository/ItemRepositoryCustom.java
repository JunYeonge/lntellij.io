package com.shop.repository;

import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getMainItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
// ItemSearchDto itemSearchDto 검색조건을 DTO
// jpa 에서 제공하는 페이징과 정렬정보를 갖고 있는 Pageable 객체(번호, 페이지크기, 정렬조건)
// 페이징된 결과를 Page 객체 반환  이 Page 객체는 페이징 정보와 함께 실제 검색결과 Item 엔티티를 담고 있음