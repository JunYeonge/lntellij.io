package com.bed.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CartOrderDto {
// 장바구니에서 상품 주문 Dto

    private Long cartItemId;

    private List<CartOrderDto> cartOrderDtoList;
}
