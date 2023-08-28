package com.bed.entity;

import com.bed.constant.*;
import com.bed.dto.MatDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Mattress")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Mat {
    @Id
    @Column(name = "mat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @GenericGenerator(name="increment",strategy = "increment")
    private Long id;
    private String title;
    @Lob
    private String content;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Age age;
    @Enumerated(EnumType.STRING)
    private Height height;
    @Enumerated(EnumType.STRING)
    private Weight weight;
    private String image; //이미지 url 주소가 저장됨
    public static MatDto fromMat(Mat mat) {
        MatDto matDTO = new MatDto();
        matDTO.setAge(mat.getAge());
        matDTO.setContent(mat.getContent());
        matDTO.setWeight(mat.getWeight());
        matDTO.setHeight(mat.getHeight());
        matDTO.setGender(mat.getGender());
        matDTO.setImage(mat.getImage());
        return matDTO;
    }
}
