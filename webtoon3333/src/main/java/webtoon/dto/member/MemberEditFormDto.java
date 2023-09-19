package webtoon.dto.member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberEditFormDto{

    private String email;

    private String nickname;

    private AddressFormDto address;

    private String newPassword;

    private String confirmPassword;

    public boolean isNewPasswordProvided() {
        return newPassword != null && !newPassword.isEmpty();
    }

}