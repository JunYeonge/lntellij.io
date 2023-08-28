package com.bed.dto;

import com.bed.entity.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@ToString
public class MemberEditFormDto{


    private String email;
//    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    private String nickname;

//    @NotEmpty(message = "전화번호는 필수 입력 사항입니다.")
    private String phoneNumber;

    // 주소 관련 필드는 AddressFormDto를 활용하여 구현할 수 있습니다.
    // AddressFormDto address; 를 Address address; 로 변경 하였음
    private Address address;

    // 새로운 암호와 암호 확인 필드는 선택적으로 수정 가능하도록 설정하였습니다.

//    @Pattern(regexp = "(?=.*[0-9])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자, 숫자, 특수문자를 사용하세요.")
    private String newPassword;

//    @NotBlank(message = "비밀번호는 비울 수 없습니다.")
    private String confirmPassword;

    public boolean isNewPasswordProvided() {
        return newPassword != null && !newPassword.isEmpty();
    }

    // getter, setter, toString 등의 메서드 생략
}