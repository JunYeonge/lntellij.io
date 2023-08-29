package toyproject.board.detail;


// 시큐리티가 login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 session 을 만들어 준다. ( Security ContextHolder 에 세션 정보를 저장)
// 오브젝트 -> Authenticaiton 타입 객체만 들어갈 수 있다.
// Authentication 안에 user 정보가 있어야 함
// user 오브젝트 타입 -> UserDetails 타입 객체

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import toyproject.board.domain.Member;
import toyproject.board.dto.MemberDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

// Security Session 영역 (Security ContextHolder) => Authentication 객체가 필요함 => 해당 객체 안에 유저 정보가 들어가 있음 => UserDetails 타입
@Data
public class PrincipalDetails implements UserDetails, OAuth2User {

    private Member member;
    private Map<String, Object> attributes;

    // 일반 사용자
    public PrincipalDetails(Member member) {
        this.member = member;
    }

    // 소셜 로그인 사용자 Oauth 로그인
    public PrincipalDetails(Member member,Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }
    public PrincipalDetails(MemberDto memberDto, Map<String, Object> attributes) {
        this.member = member;
        this.attributes = attributes;
    }

    //해당 유저의 권한을 return 하는 곳!
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        member.getRole(); 이 기존인데, Collection 타입이므로 변경해야한다.
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    // 계정 만료됐는지 확인 -> true 면 아니요.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠겼는지 확인 -> true 면 아니요.
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 계정 만료됐는지 확인 -> true 면 아니요.
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정 만료됐는지 확인 -> true 면 아니요.
    @Override
    public boolean isEnabled() {

        // 우리 사이트에서 1년동안 회원이 로그인을 안하면 휴면 계정으로 하기로 함.
        // domain 에 Timestamp 로 loginDate 를 찍어준다.
//        member.getLoginDate();
        // 현재시간 - 로그인 시간 -> 1년을 초과하면 return false 로 설정.
        return true;
    }

    @Override
    public <A> A getAttribute(String name) {
        return OAuth2User.super.getAttribute(name);
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    //attributes.get("sub")
    @Override
    public String getName() {
        return null;
    }
}
