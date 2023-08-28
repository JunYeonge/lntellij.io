package com.bed.entity;



import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="as_form_img")
@Getter
@Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AsFormImg extends BaseEntity {

    @Id
    @Column(name="as_form_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String asImgName; //이미지 파일명
    private String asOriImgName; //원본 이미지 파일명
    private String asImgUrl; //이미지 조회 경로

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "as_form_id")
    private AsForm asForm;

    public void updateAsImg(String asOriImgName, String asImgName, String asImgUrl){
        this.asOriImgName = asOriImgName;
        this.asImgName = asImgName;
        this.asImgUrl = asImgUrl;
    }
    //이미지파일명, 업데이트할 이미지 파일명, 이미지 경로를 입력받아
    //이미지 경로를 업데이트 하는 메소드

}
