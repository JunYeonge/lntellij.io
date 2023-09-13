package webtoon.entity.member;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import webtoon.constant.Role;
import webtoon.dto.member.MemberFormDto;
import webtoon.entity.BaseEntity;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;
import webtoon.entity.episodes.EpisodeComment;
import webtoon.entity.webtoon.Webtoon;

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
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column
    private String user_id;

    @Column
    private String password;   // 암호

    @Column
    private String name;    // 이름

    @Column
    private String email;   // 이메일

    @Column
    private String address; // 주소

    private String postcode;
    private String detailAddress;
    private String extraAddress;
    private String merge_address;

    @Column
    private String nickname; // 닉네임

    @Column
    private int point;  // 포인트

    @Column
    private int age;    // 나이
    @Column
    private String age_range;

    @Column
    private String gender; // 성별

    @Column
    private String residentIdFront; // 주민등록번호 앞 6자리

    @Column
    private String residentIdBack;  // 주민등록번호 뒷 7자리

    @Enumerated(EnumType.STRING)
    private Role role;


    private String provider;    // oauth2를 이용할 경우 어떤 플랫폼을 이용하는지
    private String providerId;  // oauth2를 이용할 경우 아이디값

    private boolean active;

    private LocalDateTime deactivatedAt;


    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.REMOVE}
    )
    private List<Webtoon> webtoons = new ArrayList();

    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.REMOVE}
    )
    private List<EpisodeComment> episodeComments = new ArrayList();

    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.REMOVE}
    )
    private List<PurchaseHistory> purchaseHistories = new ArrayList();


    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.REMOVE}
    )
    private List<BoardComment> boardComments = new ArrayList();

    @OneToMany(
            mappedBy = "member",
            cascade = {CascadeType.REMOVE}
    )
    private List<Board> boards = new ArrayList();

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
    public Member(String name, String password, String email, Role role, String provider, String providerId, String gender, String age_range) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.provider = provider;
        this.providerId = providerId;
        this.gender = gender;
        this.age_range = age_range;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }



    public static Member createMember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());

        // 주소 설정
        member.setPostcode(memberFormDto.getAddress().getPostcode());
        member.setAddress(memberFormDto.getAddress().getAddress());
        member.setDetailAddress(memberFormDto.getAddress().getDetailAddress());
        member.setExtraAddress(memberFormDto.getAddress().getExtraAddress());

        // merge_address 설정
        String mergeAddress = memberFormDto.getAddress().getPostcode() + " " +
                memberFormDto.getAddress().getAddress() + " " +
                memberFormDto.getAddress().getDetailAddress() + " " +
                memberFormDto.getAddress().getExtraAddress();
        member.setMerge_address(mergeAddress);

        member.setNickname(memberFormDto.getNickname());
        member.setGender(memberFormDto.getGender());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        member.setRole(Role.USER);
        return member;
    }


}
