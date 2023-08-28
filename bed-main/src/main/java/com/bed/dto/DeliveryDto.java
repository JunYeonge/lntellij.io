package com.bed.dto;

import com.bed.entity.Delivery;
import com.bed.entity.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class DeliveryDto {


    @NotBlank(message = "배송지 주소를 입력해주세요.")
    private String deliveryAddress;

    @NotBlank(message = "배송지 전화번호를 입력해주세요.")
    private String deliveryPhone;

    @NotBlank(message = "배송 수령인 이름을 입력해주세요.")
    private String deliveryName;

    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public String getDeliveryPhone() {
        return deliveryPhone;
    }
    public String getDeliveryName() {
        return deliveryName;
    }

    public Delivery toEntity(Member member) {
        Delivery delivery = new Delivery();
        delivery.setDeliveryAddress(this.deliveryAddress);
        delivery.setDeliveryPhone(this.deliveryPhone);
        delivery.setDeliveryName(this.deliveryName);
        delivery.setOrder(null); // Order 객체는 createDelivery 메서드에서 설정하므로 여기서는 null로 설정

        return delivery;
    }

}
