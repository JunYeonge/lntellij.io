package com.bed.dto;


import com.bed.entity.OrderItem;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDto {
    //주문 상품 정보를 담을 OrderItemDto
    private String itemNm; //상품명
    private int count; //주문수량

    private int orderPrice; //주문금액
    private String imgUrl;//상품 이미지 경로
    //orderItemDto 클래스의 생성자로 orderItem
    // 객체와 이미지 경로를 파라미터로 받아서
    //멤버 변수값을 세팅
    private String createdBy;
    public OrderItemDto(OrderItem orderItem,
                        String  imgUrl){
        this.itemNm = orderItem.getItem().getItemNm();
        //orderItem 객체에서 해당 주문상품의 정보를 가져와서
        //가지고 온 상품 정보객체에서
        //getItemNm() -> 호출해서 상품이름을 가져온다.

        this.createdBy = orderItem.getCreatedBy();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;

        this.createdBy = orderItem.getCreatedBy();
    }
    public String getCreatedBy() {
        return this.createdBy;
    }



}
