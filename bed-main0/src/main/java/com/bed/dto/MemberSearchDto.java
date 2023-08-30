package com.bed.dto;

import com.bed.constant.ArticleType;
import com.bed.constant.Role;
import lombok.Data;

@Data
public class MemberSearchDto {
    private String searchDateType;
    private String searchProType; // 가입 유형
    private Role searchRoleType;
    private String searchBy; //email : 이메일(아이디), name : 이름 , phonenumnber : 전화번호
    private String searchQuery="";
}
