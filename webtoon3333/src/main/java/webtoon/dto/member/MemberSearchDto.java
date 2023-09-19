package webtoon.dto.member;

import lombok.Data;
import webtoon.constant.Role;

@Data
public class MemberSearchDto {
    private String searchDateType;
    private String searchProType; // 가입 유형
    private Role searchRoleType;
    private String searchBy; //email : 이메일(아이디), name : 이름
    private String searchQuery="";
}
