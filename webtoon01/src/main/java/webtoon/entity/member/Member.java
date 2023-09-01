package webtoon.entity.member;

import lombok.Getter;
import lombok.Setter;
import webtoon.constant.Role;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Member {   // 회원

    @Id
    @GeneratedValue
    @Column(name="member_id")
    private Long id;

    @Column
    private String user_id;

    @Column
    private int password;   // 암호

    @Column
    private String name;    // 이름

    @Column
    private String email;   // 이메일

    @Column
    private String address; // 주소

    @Column
    private String nickname; // 닉네임

    @Column
    private int point;  // 포인트

    @Column
    private int age;    // 나이

    @Column
    private String gender;  // 성별

    @Enumerated(EnumType.STRING)
    private Role role;



}
