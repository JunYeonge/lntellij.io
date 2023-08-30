package com.bed.dto;

import com.bed.entity.AsForm;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
public class AsFormDto {
    private Long id;
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    private String tel;
    @NotBlank
    private String tel1;
    @NotBlank
    private String tel2;
    @NotBlank
    private String tel3;
    @NotBlank
    private String home_zip1;
    @NotBlank
    private String home_zip2;
    @NotBlank
    private String home_zip5;
    @NotBlank
    private String road_addr;
    @NotBlank
    private String home_addr;
    @NotBlank
    private String home_addr2;
    private String rhome_addr2;


    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email1;

    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email2;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String fileName;

    @NotBlank
    private String model;

    private AsFormImgDto asFormImgDto = new AsFormImgDto();


    private static ModelMapper modelMapper = new ModelMapper();
    public AsForm createAsForm(){
        return modelMapper.map(this, AsForm.class);
    }
    public static AsFormDto of(AsForm asForm){
        return modelMapper.map(asForm, AsFormDto.class);
    }

    // 생성자

    public String getTel() {
        return tel = tel1 + tel2 + tel3;
    }

    public void setTel(String tel1, String tel2, String tel3) {
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.tel3 = tel3;
    }


    public String getModelName() {
        return model;
    }

    public void setModelName(String modelName) {
        this.model = modelName;
    }


}
