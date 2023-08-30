package com.bed.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AsFormSearchDto {
    private String searchDateType;
    private String searchBy; // model : 모델, email:이메일, tel : 전화번호
    private String searchQuery="";
}
