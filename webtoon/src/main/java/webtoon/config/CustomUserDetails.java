package webtoon.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import webtoon.config.userinfo.OAuth2UserInfo;
import webtoon.entity.member.Member;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Getter
@Setter

public class CustomUserDetails implements UserDetails, OAuth2User {
    private Member member;
    private String email;
    private String password;
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    private OAuth2UserInfo oAuth2UserInfo;

    private String name;
    private boolean authenticated;

    public CustomUserDetails(Member member){
        this.member = member;
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = member.getNickname();
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString()));
        this.name = member.getName();
        this.authenticated = true;
    }


    public CustomUserDetails(Member member, OAuth2UserInfo oAuth2UserInfo) {
        this.member = member;
        this.oAuth2UserInfo = oAuth2UserInfo;
        this.email = member.getEmail();
        this.password = member.getPassword();
        this.nickname = oAuth2UserInfo.getNickname();  // attributes 대신에 oAuth2UserInfo.getNickname()을 사용
        this.authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString()));
        this.attributes = oAuth2UserInfo.getAttributes();  // attributes 대신에 oAuth2UserInfo.getAttributes()를 사용
        this.authenticated = true;
    }

    //UserDetails 구현 해당 유저의 권한목록 리턴

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return member.getRole().toString();
            }
        });
        return collect;
    }
    @Override

    public String getPassword(){
        return member.getPassword();
    }


    @Override
    public String getUsername(){
        return member.getEmail();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return  true;
    }

    @Override
    public boolean isEnabled() {
        return  true;
    }



    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }
    //      OAuth2User 구현 @return
    @Override
    public String getName(){

        return name;
    }



}