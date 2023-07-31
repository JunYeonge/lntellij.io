package jpabook.ipashop.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private  Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //주문 회원
    //Order 엔티티 클래스에 Member 라는 필드가 있으며
    //Member 엔티티과 다대일관계(@ManyToOne) 이며
    //member_id 컬럼과 연결

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송정보

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    //== 연관관계 메서드 ==//
    public void setMember(Member member){
        this.member = member;
        // 현재 엔티티에서 member 엔티티를설정
        // order 엔티티가 member 엔티티를 참조
        member.getOrders().add(this);
        // 주어진 order 엔티티를 Member 엔티티의 Order
        // 엔티티를 Member 엔티티의 order 켈렉션에 추가하는 부분
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
}
