package com.bed.dto;

import com.bed.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType; //조회시간
    //all , 1d-하루 , 1w -일주, 1m - 한달 , 6m

    private ItemSellStatus searchSellStatus;
    //판매상태
    private String searchBy;
    //itemNm:상품명  , createdBy : 상품 등록자 아이디
    private String searchQuery = "";
    //조회할 검색어 저장 변수
    // searchBy - itemNm일 경우 상품평기준
}