package webtoon.dto.member;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 입력 사항 입니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 사항 입니다.")
    private String nickname;

    @NotEmpty(message = "이메일은 필수 입력 사항 입니다.")
    @Email(message = "이메일 형식으로 입력해주세요")
    private String email;

    @NotEmpty(message = "비밀먼호는 필수 입력 사항 입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @NotEmpty(message = "비밀번호 확인은 필수 입력 사항 입니다.")
    private String confirmPassword;

    @NotEmpty(message = "주민등록번호 앞자리6개는 필수 입력 값입니다.")
    private String residentIdFront;

    @NotEmpty(message = "주민등록번호 뒷자리7개는 필수 입력 값입니다.")
    private String residentIdBack;


    private AddressFormDto address;
    private String merge_address;
    private String gender;

    private String newPassword;
    private String currentPassword; // 현재 암호 필드 추가



}