package webtoon.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfoFormDto {

    private Long id;

    private String reg_time;

    private String update_time;

    private String address;
    private int age;
    private String email;

    private String gender;
    private String nickName;

    private int point;
}
