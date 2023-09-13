package webtoon.config;

//import com.bed.service.CustomOauth2UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import webtoon.service.member.MemberService;
import webtoon.service.member.OAuth2DetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final MemberService memberService;


    @Autowired
    OAuth2DetailsService oAuth2DetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;




    @Autowired
    public SecurityConfig(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeRequests()
                .antMatchers("/CsCenter/asSubmit").permitAll()
                .antMatchers("/CsCenter/notices").permitAll()
                .mvcMatchers("/css/**", "/js/**", "/img/**").permitAll()
                .mvcMatchers("/","/members/**","/item/**","/images/**","/**").permitAll()
                .mvcMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http
                .csrf().ignoringAntMatchers("/api/**") /* REST API 사용 예외처리 */
                .and()
                .formLogin()
                .loginPage("/members/login")
                .defaultSuccessUrl("/main")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/main")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");

        http
                .oauth2Login()
                .loginPage("/members/login")
                .defaultSuccessUrl("/main")

//                .successHandler(loginSuccess)
                .userInfoEndpoint() // OAuth2 로그인 성공 후 가져올 설정들
                .userService(oAuth2DetailsService); // 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        return http.build();
    }

}
