package com.bed.dto;

import com.bed.constant.Age;
import com.bed.constant.Gender;
import com.bed.constant.Height;
import com.bed.constant.Weight;
import com.bed.entity.Mat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class MatDto {
    private Long id;
    private String title;
    private String content;
    private Gender gender;
    private Age age;
    private Height height;
    private Weight weight;
    private String image;




//    public Mat toEntity(){
//        return new Mat(id,title,gender,age,height,weight);
//    }
// Mat 엔티티 객체로부터 MatDTO 객체를 생성하는 정적 팩토리 메서드를 추가할 수 있습니다.

}
