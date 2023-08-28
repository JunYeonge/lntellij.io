package com.bed.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {
    private Long id;
    private String address;
    private String imgUrl;
    private int productId;
    private int amount;
    private int pointAmount;
    private int flag; //상세인지 장바구니인지 구분

}
