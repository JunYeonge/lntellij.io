package com.bed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDetailDto {

    private Long cartItemId;
    // ㄴ 장바구니 상품 아이디

    private String itemNm;
    // ㄴ 상품명

    private int price;
    // ㄴ 상품 금액

    private int count;
    // ㄴ 수량

    private String imgUrl;
    // ㄴ 상품 이미지 경로

    public CartDetailDto(Long cartItemId, String itemNm, int price, int count, String imgUrl){
        this.cartItemId = cartItemId;
        this.itemNm = itemNm;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;
    }
}
