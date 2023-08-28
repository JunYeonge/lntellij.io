package com.bed.repository;

import com.bed.dto.OrderHistDto;
import com.bed.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getOrderById(Long orderId);
    @Query("select o from Order o " +
            "where o.member.email = :email "  +
            "order by o.orderDate desc"
    )
    List<Order> findOrders(@Param("email") String email,
                           Pageable pageable);
    //현재 로그인한 사용자의 주문 데이터를 페이지 조건에 맞추어서 조회
    //'email' 파라미터를 사용하여 현재 로그인한
    // 사용자의 이메일과 일치하는 주문데이터를 조회

    @Query("SELECT o FROM Order o ORDER BY o.orderDate DESC")
    List<Order> findAllOrderByOrderDateDesc();


    @Query("select count(o) from Order o " +
            "where o.member.email = :email"
    )
    Long countOrder(@Param("email") String email);
    //현재 로그인한 회원의 주문 개수가 몇 개인지 조회

    @Query(value = "SELECT * FROM orders WHERE username = :email ORDER BY order_date DESC", nativeQuery = true)
    Page<OrderHistDto> findLatestOrdersByUsername(@Param("email") String email, Pageable pageable);

}
