package webtoon.service.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import webtoon.config.CustomUserDetails;
import webtoon.config.userinfo.KakaoUserInfo;
import webtoon.config.userinfo.OAuth2UserInfo;
import webtoon.constant.Role;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;

import java.util.UUID;

@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService {

    @Autowired
    private MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        OAuth2UserInfo oAuth2UserInfo = null;

        String provider = userRequest.getClientRegistration().getRegistrationId();

        if(provider.equals("kakao")){
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String gender = oAuth2UserInfo.getGender();
        String ageRange = oAuth2UserInfo.getAge_Range();

        Role role = Role.USER;

        // 중복 체크
        Member byEmail = memberRepository.findByEmail(email);

        // DB에 이미 등록된 사용자인 경우에는 업데이트 처리
        if (byEmail == null) {
            // 회원 가입 처리
            String username = provider + "_" + providerId;
            String uuid = UUID.randomUUID().toString().substring(0, 6);
            String password = passwordEncoder.encode("패스워드" + uuid);

            String nickname = oAuth2UserInfo.getNickname();

            Member newMember = Member.oauth2Register()
                    .name(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .gender(gender)
                    .age_range(ageRange)
                    .build();

            newMember.setNickname(nickname);

            memberRepository.save(newMember);

            return new CustomUserDetails(newMember, oAuth2UserInfo);
        }

        // DB에 이미 등록된 사용자인 경우에는 업데이트 처리
        byEmail.setProvider(provider);
        byEmail.setProviderId(providerId);
        byEmail.setGender(gender);
        byEmail.setAge_range(ageRange);
        memberRepository.save(byEmail);

        return new CustomUserDetails(byEmail, oAuth2UserInfo);
    }

}