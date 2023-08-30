package com.bed.service;

import com.bed.dto.PayDetailResponseDto;
import com.bed.entity.Item;

import com.bed.entity.ItemImg;
import com.siot.IamportRestClient.IamportClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.bed.entity.QItem.item;
import static com.bed.entity.QItemImg.itemImg;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final IamportClient iamportClient;

    public PaymentService() {
        this.iamportClient = new IamportClient("3361655256577743","rHKk028PjZVXOHCBs505RgTmkKCqO6kfLeGYT7TwGsTP8ohXb7RjQX1U8T1p4vVAfSIMJF8WyL3tDjoX");
    }

    //DetailPayment페이지로 price(총금액), product(상품정보) 묶어서 DTO로 보내기
    public PayDetailResponseDto makeDetailResponseDto(Item item, int amount, ItemImg itemImg){
        int totalPrice = item.getPrice() * amount; //총금액 계산

        PayDetailResponseDto payDetailResponseDto = new PayDetailResponseDto(
                item.getId(),
                itemImg.getImgUrl(),
                item.getBrand(),
                item.getItemNm(),
                item.getPrice(),
                amount,
                totalPrice
        );
        return payDetailResponseDto;
    }




}
