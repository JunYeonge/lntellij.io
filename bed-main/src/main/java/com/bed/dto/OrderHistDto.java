package com.bed.dto;


import com.bed.entity.Order;
import com.bed.constant.OrderStatus;
import com.bed.entity.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class OrderHistDto {

    public OrderHistDto(Order order){

    this.orderId = order.getId();
        this.orderDate = order.getOrderDate() != null ? order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : null;
    //HH 24시 기준, hh는 12시간 형식,
    //M이 하나면 1,2,3 MM이면 01,02,03   mm은 분
    this.orderStatus = order.getOrderStatus();

    }


    private Long orderId; //주문아이디
    private String orderDate; //주문 날짜
    private OrderStatus orderStatus; //주문 상태

    private List<OrderItemDto> orderItemDtoList
            = new ArrayList<>();
    //주문 상품 리스트

    private List<OrderItemDto> orderItemDtos = new ArrayList<>();

    public List<OrderItemDto> getOrderItemDtos() {
        return orderItemDtos;
    }

    public void addOrderItemDto(OrderItemDto orderItemDto){
        orderItemDtoList.add(orderItemDto);
        //orderItemDto 객체를 주문 상품 리스트에 추가하는 메소드
    }

    public void setOrderItemDtos(List<OrderItemDto> orderItemDtos) {
        this.orderItemDtos = orderItemDtos;
    }
}
