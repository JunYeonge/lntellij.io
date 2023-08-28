package com.bed.dto;

import com.bed.constant.AsFormStatus;
import com.bed.entity.AsForm;
import lombok.Getter;
import lombok.Setter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
//AS신청 내역을 전달하기 위한 dto
public class AsHistDto {
    private Long asFormId; //신청 아이디
    private String title; //제목
    private int seqNumber; //넘버링
    private String asFormCreateBy; //신청 이메일
    private String asFormDate; //신청날짜
    private AsFormStatus asFormStatus; //신청상태

    public AsHistDto(AsForm asForm){
        this.asFormId = asForm.getId();
        this.title = asForm.getTitle();
        this.asFormCreateBy = asForm.getCreatedBy();
        this.asFormDate = asForm.getRegTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.asFormStatus = asForm.getAsFormStatus();
    }

    private List<AsFormDto> asFormDtoList = new ArrayList<>();

}
