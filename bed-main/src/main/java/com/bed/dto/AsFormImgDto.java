package com.bed.dto;

import com.bed.entity.AsFormImg;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class AsFormImgDto {

    private Long id;
    private String asImgName;
    private String asOriImgName;
    private String asImgUrl;
//    private MultipartFile asFormImgFile;
    private static ModelMapper modelMapper = new ModelMapper();

    public static AsFormImgDto of(AsFormImg asFormImg){
        return modelMapper.map(asFormImg,AsFormImgDto.class);
    }
    private String fileName; //파일명 저장 필드
    private MultipartFile file; //업로드 된 파일을 저장하기 위한 필드

}
