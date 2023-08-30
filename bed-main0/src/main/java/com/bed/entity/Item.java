package com.bed.entity;

import com.bed.constant.*;
import com.bed.dto.ItemFormDto;
import com.bed.exception.OutOfStockException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "item")
@Getter
@Setter
@ToString
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false )
    private Long id; // 식별자

    @Column(nullable = false)
    private String itemNum; //상품 번호

    @Column(nullable = false)
    private String itemNm; //상품 명

    @Column(name="price",nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private double tax; //부가세

    @Column(nullable = false)
    private double pre_tax; //세전가격

    @Column(nullable = false)
    private int stockNum; //재고 수량

    @Lob
    @Column(nullable = false)
    private String itemDetail; //상품 상세설명
    
    @Column(nullable = false)
    private String brand; //상품 브랜드


    @Enumerated(EnumType.STRING)
    private Type type;//타입
    @Enumerated(EnumType.STRING)
    private Color color;//색상
    @Enumerated(EnumType.STRING)
    private Size size;//사이즈


    //private OrderStatus orderStatus;//
    @Enumerated(EnumType.STRING)
    private OrderDelivery orderDelivery;//
    //배송방법(택배, 퀵, 자체배송,테이크아웃 등)
    //(DELIVERY,QUICK,...,TACKOUT
    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus;//
    //상품판매상태
    //(SELL ,SOLDOUT)


    public void updateItem(ItemFormDto itemFormDto) {
        this.itemNm = itemFormDto.getItemNm();
        this.price = itemFormDto.getPrice();
        this.tax = itemFormDto.getTax();
        this.pre_tax = itemFormDto.getPre_tax();
        this.stockNum = itemFormDto.getStockNum();
        this.itemDetail = itemFormDto.getItemDetail();
        this.brand = itemFormDto.getBrand();
        this.type = itemFormDto.getType();
        this.color = itemFormDto.getColor();
        this.size = itemFormDto.getSize();
        this.orderDelivery = itemFormDto.getOrderDelivery();
        this.itemSellStatus = itemFormDto.getItemSellStatus();
    }
    public void removeStock(int stockNumber) {
        int restStock = this.stockNum - stockNumber;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다.(현재 재고 수량: " + this.stockNum + ")");
        }
        this.stockNum = restStock;
    }

    public void addStock(int stockNumber) {
        this.stockNum += stockNumber;
    }
    //주문을 취소할 경우 주문 수량만큼 상품의 재고를 증가시키는 메소드를 구현합니다.
}
