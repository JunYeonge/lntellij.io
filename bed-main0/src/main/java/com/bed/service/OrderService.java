package com.bed.service;

import com.bed.dto.CartOrderDto;
import com.bed.dto.OrderDto;
import com.bed.dto.OrderHistDto;
import com.bed.dto.OrderItemDto;
import com.bed.entity.*;
import com.bed.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemImgRepository itemImgRepository;

    //    Controller에서 전달받은 email을 통해 member와 Dto 내부의 item_id를 사용해서 item을 가져옵니다.
//    그리고 item과 Dto의 count를 사용해서 OrderItem를 생성한 뒤, order를 만들고 저장합니다.
    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId()) //주문할 상품조회
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);
        //현재 로그인한 회원의 이메일 정보를 이용하여 회원정보 조회
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        //주문할 상품 엔티티와 주문 수량을 이용하여 주문 상품 엔티티를 생성합니다.
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        //회원정보와 주문할 상품 리스트 정보를 이용하여 주문 엔티티를 생성합니다.
//        orderRepository.save(order);
        orderRepository.save(order);
        //생성한 주문 엔티티를 저장합니다
        return order.getId();
    }
    @Transactional(readOnly = true) // 유저
    public Page<OrderHistDto> getOrderList(String email, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(email, pageable);
        //유저의 아이디와 페이징 조건을 이용하여 주문 목록 조회

        Long totalCount = orderRepository.countOrder(email);
        //유저의 주문 총 개수를 구합니다.

        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for (Order order : orders) {
            //주문리스트를 순회하면서 구매 이력 페이지에 전달 DTO를 생성
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y");
                //주문한 상품의 대표이미지를 조회

                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
        //페이지 구현  객체를 생성하여 반환
    }

    @Transactional(readOnly = true) // 유저
    public Page<OrderHistDto> getOrderCartList(String email, Pageable pageable) {
        List<Order> orders = orderRepository.findOrders(email, pageable);
        //유저의 아이디와 페이징 조건을 이용하여 주문 목록 조회

        Long totalCount = orderRepository.countOrder(email);
        //유저의 주문 총 개수를 구합니다.

        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for (Order order : orders) {
            //주문리스트를 순회하면서 구매 이력 페이지에 전달 DTO를 생성
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            List<OrderItemDto> orderItemDtos = new ArrayList<>();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn
                        (orderItem.getItem().getId(), "Y");
                //주문한 상품의 대표이미지를 조회

                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, itemImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDto.setOrderItemDtos(orderItemDtos);
            orderHistDtos.add(orderHistDto);
        }
        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
        //페이지 구현  객체를 생성하여 반환
    }

    @Transactional(readOnly = true)
    public Page<OrderHistDto> getAllOrderList(Pageable pageable) {
        List<Order> orders = orderRepository.findAllOrderByOrderDateDesc();
        Long totalCount = orderRepository.count();

        List<OrderHistDto> orderHistDtos = new ArrayList<>();
        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                ItemImg itemImg = itemImgRepository.findByItemIdAndRepimgYn(orderItem.getItem().getId(), "Y");
                OrderItemDto orderItemDto = new OrderItemDto(orderItem, itemImg.getImgUrl());
                // 작성자 정보 설정
                orderItemDto.setCreatedBy(orderItem.getCreatedBy());
                orderHistDto.addOrderItemDto(orderItemDto);
            }
            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<>(orderHistDtos, pageable, totalCount);
    }

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String email){
        Member curMember = memberRepository.findByEmail(email);
        //로그인한 사용자
        Order order = orderRepository.findById(orderId)
                //주문데이터를 생성한 사용자

                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();


        if(!StringUtils.equals(curMember.getEmail(),
                savedMember.getEmail())){
            return false;
            //로그인한 사용자와 주문한 사용자가 다르면 삭제 못하게.
        }
        return true;
        //false일때 작동.
    }

    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
        //주문 취소 상태로 변경하면 변경감지 기능에 의해서 트랜잭션이 끝날때
        //update 쿼리가 실행 된다.
    }


    // ------------------ 장바구니 에서 상품을 주문----------------
    public Long orders(List<OrderDto> orderDtoList, String email){

        Member member = memberRepository.findByEmail(email);
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderDto orderDto : orderDtoList) {
            Item item = itemRepository.findById(orderDto.getItemId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderItem orderItem = OrderItem.createOrderItem(item,
                    orderDto.getCount());
            orderItemList.add(orderItem);
        }//주문할 상품의 리스트를 만들어 줍니다.


        Order order = Order.createOrder(member, orderItemList);
        // 현재 로그인한 회원과 주문상품목록을 이용하여 주문 엔티티를 만듭니다.

        orderRepository.save(order);

        return order.getId();
    }
    public Order getOrderById(Long orderId) {
        // orderId를 사용하여 주문을 조회하는 로직을 구현
        // 조회한 주문을 반환
        return orderRepository.getOrderById(orderId);
    }


}
