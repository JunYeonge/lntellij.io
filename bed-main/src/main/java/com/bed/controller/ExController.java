package com.bed.controller;

import com.bed.constant.*;
import com.bed.dto.ItemDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping(value = "/thymeleaf")
public class ExController {

    @GetMapping(value = "/ex01")
    public String thymeleafEx01(Model model) {
        int price = 10000; // 가격
        double taxRate = 0.1; // 부가세율

        // 세전가격 계산
        double pre_tax = price / (1 + taxRate);
        // 부가세 계산
        double tax = price - pre_tax;

        ItemDto itemDto = new ItemDto();
        itemDto.setItemNm("테스트 상품");
        itemDto.setItemNum(String.valueOf(LocalDateTime.now().
                format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))));
        itemDto.setStockNum(100);
        itemDto.setPrice(price);
        itemDto.setTax(tax);
        itemDto.setPre_tax(pre_tax);
        itemDto.setItemDetail("아이템 상세 설명입니다.");
        itemDto.setBrand("브랜드");
        itemDto.setType(Type.AC);
        itemDto.setColor(Color.WGB);
        itemDto.setSize(Size.KING);
        itemDto.setOrderDelivery(OrderDelivery.QUICK);
        itemDto.setItemSellStatus(ItemSellStatus.SELL);
        itemDto.setRegTime(LocalDateTime.now());

        long roundedTax = Math.round(itemDto.getTax());
        long roundedPreTax = Math.round(itemDto.getPre_tax());

        model.addAttribute("tax", roundedTax);
        model.addAttribute("pre_tax", roundedPreTax);

        model.addAttribute("itemDto", itemDto);

        return "thymeleafEX/ex01";
    }
}
