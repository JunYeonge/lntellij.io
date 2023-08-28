package com.bed.entity;



import com.bed.constant.AsFormStatus;
import com.bed.dto.AsFormDto;
import com.bed.dto.AsFormImgDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "as_form")
@Getter
@Setter
@ToString
public class AsForm extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "as_form_id")
    private Long id;
    private String name;
    @Column(name = "tel")
    private String tel;
    private String home_zip1;
    private String home_zip2;
    private String home_zip5;
    private String road_addr;
    private String home_addr;
    private String home_addr2;
    private String rhome_addr2;

    @Column(unique = true)
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;

    private String title;
    private String content;
    private String fileName;

    @Column(nullable = false, length = 50)
    private String model;

    @OneToOne(mappedBy = "asForm", cascade = CascadeType.ALL)
    private AsFormImg asFormImg;

    @Enumerated(EnumType.STRING)
    private AsFormStatus asFormStatus; //신청 상태

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 매핑할 외래 키 컬럼 지정
    private Member member;

    //6/15일 정선 추가
    @OneToMany(mappedBy = "asForm", cascade = CascadeType.ALL,
            orphanRemoval = true)    // 양방향 연관관계
    private List<Comment> comments = new ArrayList<>();


    public void setTel(String tel1, String tel2, String tel3) {
        this.tel = tel1 + tel2 + tel3;
    }

    @Transient
    public String getEmail1() {
        if (email != null && email.contains("@")) {
            return email.split("@")[0];
        }
        return "";
    }

    @Transient
    public String getEmail2() {
        if (email != null && email.contains("@")) {
            return email.split("@")[1];
        }
        return "";
    }

    public void setEmail(String email1, String email2) {
        if (email1 != null && email2 != null) {
            this.email = email1 + "@" + email2;
        }
    }

    public static AsForm fromDto(AsFormDto dto) {
        AsForm asForm = new AsForm();
        asForm.setName(dto.getName());
        asForm.setTel(dto.getTel());
        asForm.setHome_zip1(dto.getHome_zip1());
        asForm.setHome_zip2(dto.getHome_zip2());
        asForm.setHome_zip5(dto.getHome_zip5());
        asForm.setRoad_addr(dto.getRoad_addr());
        asForm.setHome_addr(dto.getHome_addr());
        asForm.setHome_addr2(dto.getHome_addr2());
        asForm.setRhome_addr2(dto.getRhome_addr2());
        asForm.setEmail(dto.getEmail1(), dto.getEmail2());
        asForm.setTitle(dto.getTitle());
        asForm.setContent(dto.getContent());
        asForm.setFileName(dto.getFileName());
        asForm.setModel(dto.getModelName());
        AsFormStatus asFormStatus = AsFormStatus.DELAY; // AsFormStatus 값을 지정
        asForm.setAsFormStatus(asFormStatus);
        return asForm;
    }

}
