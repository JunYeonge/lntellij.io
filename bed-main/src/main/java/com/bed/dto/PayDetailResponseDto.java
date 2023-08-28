package com.bed.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PayDetailResponseDto {
    private Long id;
    private String url;
    private String brand;
    private String name;
    private int price;
    private int amount;
    private int totalPrice;
}
