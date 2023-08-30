package com.bed.dto;

import com.bed.constant.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ItemDto {

    private Long id;

    private String itemNum;

    private String itemNm;

    private int price;

    private double tax;

    private double pre_tax;

    private int stockNum;

    private String itemDetail;

    private String brand;

    private Type type;

    private Color color;

    private Size size;

    private OrderDelivery orderDelivery;

    private ItemSellStatus itemSellStatus;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;

}
