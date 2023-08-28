package com.bed.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "cart_item")
public class CartItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;

    // --------- 장바구니에 담을 상품 엔티티를 생성하는 메소드(createCartItem) --------------
    public static CartItem createCartItem(Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    // -------------- 장바구니 담을 수량을 증가시켜주는 메소드(addCount)----------------
    public void addCount(int count){
        this.count += count;
    }

    //-------------장바구니 상품 수량 변경하는 메소드(updateCount)-----------------
    public void updateCount(int count){
        this.count = count;
    }
}
