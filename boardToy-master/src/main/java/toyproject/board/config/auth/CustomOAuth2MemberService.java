package toyproject.board.config.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import toyproject.board.config.auth.provider.*;
import toyproject.board.detail.PrincipalDetails;
import toyproject.board.domain.Member;
//import toyproject.board.dto.OAuthAttributes;
//import toyproject.board.dto.SessionMember;
import toyproject.board.domain.Role;
import toyproject.board.dto.MemberDto;
import toyproject.board.repository.MemberRepository;
import toyproject.board.service.MemberService;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2MemberService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;
    private final HttpSession httpSession;
    private final MemberService memberService;



    // 함수 종료시 @AuthenticationPrincipal 어노테이션이 만들어진다.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        System.out.println("userRequest = " + userRequest.getAccessToken().getTokenValue());
        // 구글 로그인버튼 클릭 -> 구글 로그인창 -> 로그인을 완료 -> code 를 리턴(Oauth-client 라이브러리) -> AccessToken 요청
        // userRequest 정보 -> loadUser함수 호출 -> 구글로부터 회원프로필을 받아준다.
        System.out.println("userRequest = " + userRequest.getClientRegistration());
        System.out.println("oAuth2User = " + userRequest.getAdditionalParameters());
        System.out.println("oAuth2User.getAttributes() = " + oAuth2User.getAttributes());
        System.out.println("oAuth2User.getAuthorities() = " + oAuth2User.getAuthorities());
        System.out.println("oAuth2User.getName() = " + oAuth2User.getName());

        OAuth2UserInfo oAuth2UserInfo = null;
        if(userRequest.getClientRegistration().getRegistrationId().equals("google")){
            System.out.println("구글 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("facebook")){
            System.out.println("페이스북 로그인 요청");
            oAuth2UserInfo = new FacebookUserInfo(oAuth2User.getAttributes());
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("naver")){
            System.out.println("네이버 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map)oAuth2User.getAttributes().get("response"));
        }else if(userRequest.getClientRegistration().getRegistrationId().equals("kakao")){
            System.out.println("카카오 로그인 요청");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }else{
            System.out.println("구글과 페이스북만 지원");
        }

//        String provider = userRequest.getClientRegistration().getRegistrationId(); // google
//        String providerId = oAuth2User.getAttribute("sub");
//        String username = provider + "_" + providerId; // google_1923912312312
//        String password = bCryptPasswordEncoder.encode("dodokong");
//        String email = oAuth2User.getAttribute("email");
//        String role = Role.USER.getValue();
        String provider = oAuth2UserInfo.getProvider(); // google
        String providerId = oAuth2UserInfo.getProviderId();
        String username = provider + "_" + providerId; // google_1923912312312
        String password = "dodokong";
        String email = oAuth2UserInfo.getEmail();
        String role = Role.USER.getValue();



        Member member = memberRepository.findByUsername(username);
        if (member == null) {
            System.out.println("최초 가입");
            member = Member.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            memberService.joinUserWithMember(member);
        }else {
            System.out.println("이미 가입한 적이 있습니다.");
        }
        return new PrincipalDetails(member, oAuth2User.getAttributes());
    }
}
