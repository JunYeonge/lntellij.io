package com.bed.entity;

import com.bed.constant.Role;
import com.bed.dto.MemberFormDto;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="member_id")
    private Long id;

    private String name;

    private String username;

    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="address_id")
    private Address address;

    private String merge_address;

    private String phoneNumber;
    private String nickname;
    private String sex;
    private String maritalStatus;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값

    private boolean active;

    private LocalDateTime deactivatedAt;

    //6/15 정선 추가
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)    // 양방향 연관관계
    private List<AsForm> asForms = new ArrayList<>();

    public String getProvider() {
        return provider;
    }

    @Builder(builderClassName = "UserDetailRegister", builderMethodName = "userDetailRegister")
    public Member(String name, String password, String email, String nickname ,Role role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    @Builder(builderClassName = "OAuth2Register", builderMethodName = "oauth2Register")
    public Member(String name, String password, String email, Role role, String provider, String providerId) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder){
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        // 주소 설정
        Address address = new Address();
        address.setPostcode(memberFormDto.getAddress().getPostcode());
        address.setAddress(memberFormDto.getAddress().getAddress());
        address.setDetailAddress(memberFormDto.getAddress().getDetailAddress());
        address.setExtraAddress(memberFormDto.getAddress().getExtraAddress());
        member.setAddress(address);

        // merge_address 설정
        String mergeAddress = memberFormDto.getAddress().getPostcode() + " " +
                memberFormDto.getAddress().getAddress() + " " +
                memberFormDto.getAddress().getDetailAddress() + " " +
                memberFormDto.getAddress().getExtraAddress();
        member.setMerge_address(mergeAddress); // 이 부분이 변경되었습니다.

        member.setPhoneNumber(memberFormDto.getPhoneNumber());
        member.setNickname(memberFormDto.getNickname());
        member.setSex(memberFormDto.getSex());
        member.setMaritalStatus(memberFormDto.getMaritalStatus());
        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.ROLE_USER);
        return member;
    }
        // 회원 가입 코드를 확인하여 역할 설정
//        if (memberFormDto.getSignupCode() != null && memberFormDto.getSignupCode().equals("121212")) {
//            member.setRole(Role.ROLE_ADMIN);
//        } else {
//            member.setRole(Role.ROLE_USER);
//        }
//
//        member.setRole(Role.ROLE_ADMIN);
//        return member;




}
