package com.bed.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Getter
@Setter
public class MemberFormDto {
//    private AddressFormDto address;
//
//    public MemberFormDto() {
//        this.address = new AddressFormDto();
//    }


    @NotBlank(message = "이름은 필수 입력 사항 입니다.")
    private String name;

//    @NotBlank(message = "이름은 필수 입력 사항 입니다.")
    private String username;

    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력 사항 입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀먼호는 필수 입력 사항 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 사항 입니다.")
    private String confirmPassword;

//    @NotEmpty(message = "주소는 필수 입력 사항 입니다.")

    private AddressFormDto address;
    //private String address;
    private String merge_address;



//    예정) 번호 저장시 - 를 적어도 자동으로 빠지게 하기
    @NotEmpty(message = "전화번호는 필수 입력 사항 입니다.")
    private String phoneNumber;
    //예정) html 에 선택 사항 만들어 체크 하게 하기
    private String sex;
    //예정) html 에 선택 사항 만들어 체크 하게 하기
    private String maritalStatus;

    private String newPassword;
    private String currentPassword; // 현재 암호 필드 추가

//    private String signupCode;  // 회원 가입 코드 필드 추가
//
//    // 기존의 메소드들...
//
//    public String getSignupCode() {
//        return signupCode;
//    }
//
//    public void setSignupCode(String signupCode) {
//        this.signupCode = signupCode;
//    }

}