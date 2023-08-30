package com.bed.repository;

import com.bed.constant.Color;
import com.bed.constant.Size;
import com.bed.constant.Type;
import com.bed.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>,
        QuerydslPredicateExecutor<Item>, ItemRepositoryCustom {

    List<Item> findByItemNm(String itemNm);
    List<Item> findByItemNmOrItemDetail(String itemNm,String itemDetail);

    List<Item> findByPriceLessThan(Integer price);

    List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);

    @Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);


    List<Item> findByItemDetailContainingOrderByPriceDesc(String itemDetail);

    List<Item> findByTypeInAndSizeInAndColorIn(List<Type> types, List<Size> sizes, List<Color> colors);

}
