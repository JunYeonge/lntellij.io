package com.bed.service;

import com.bed.dto.DeliveryDto;
import com.bed.entity.Delivery;
import com.bed.entity.Order;
import com.bed.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService {
    private final OrderService orderService;
    private final DeliveryRepository deliveryRepository;
    private final MemberService memberService;

    public DeliveryService(OrderService orderService, DeliveryRepository deliveryRepository, MemberService memberService) {
        this.orderService = orderService;
        this.deliveryRepository = deliveryRepository;
        this.memberService = memberService;
    }

    public Delivery createDelivery(DeliveryDto deliveryDto, Long orderId) {
        Order order = orderService.getOrderById(orderId);

        Delivery delivery = deliveryDto.toEntity(order.getMember());
        delivery.setOrder(order);
        order.setDelivery(delivery); // Optional: 오더 엔티티에도 딜리버리를 설정할 수 있습니다.

        return deliveryRepository.save(delivery);
    }



//    public List<Delivery> getAllDeliveries() {
//        return deliveryRepository.findAll();
//    }
//
//    public void updateDelivery(Long deliveryId, DeliveryDto deliveryDto) {
//        Delivery delivery = getDeliveryById(deliveryId);
//        delivery.setDeliveryAddress(deliveryDto.getDeliveryAddress());
//        delivery.setDeliveryPhone(deliveryDto.getDeliveryPhone());
//        delivery.setDeliveryName(deliveryDto.getDeliveryName());
//        deliveryRepository.save(delivery);
//    }
//
//    public void deleteDelivery(Long deliveryId) {
//        Delivery delivery = getDeliveryById(deliveryId);
//        deliveryRepository.delete(delivery);
//    }
}
