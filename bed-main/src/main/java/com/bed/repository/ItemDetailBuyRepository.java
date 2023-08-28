package com.bed.repository;

import com.bed.dto.ItemDetailBuyDto;
import com.bed.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

public interface ItemDetailBuyRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom{


    @Query("select new com.bed.dto.ItemDetailBuyDto(ci.id, ci.type, ci.color, ci.size) " +
            "from com.bed.entity.Item ci " +
            "where ci.id = :itemId")
    ItemDetailBuyDto findItemDetailBuyDto(@Param("itemId") Long itemId);






}
