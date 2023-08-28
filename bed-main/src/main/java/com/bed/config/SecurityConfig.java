package com.bed.config;

//import com.bed.service.CustomOauth2UserService;
import com.bed.config.auth.OAuth2DetailsService;
import com.bed.service.MemberService;
import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private final  MemberService memberService;
    /* OAuth */
//    private final CustomOAuth2UserService customOAuth2UserService;
//    public SecurityConfig(CustomOauth2UserService customOauth2UserService){
//        this.customOAuth2UserService = customOauth2UserService;
//    }
//    @Autowired
//    private LoginSuccess loginSuccess;

    @Autowired
    OAuth2DetailsService oAuth2DetailsService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

//    @Autowired
//    private CustomAuthenticationProvider customAuthenticationProvider;
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider);
//    }



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
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureUrl("/members/login/error")
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID");


        http
                .oauth2Login()
                .loginPage("/members/login")
//                .successHandler(loginSuccess)
                .userInfoEndpoint() // OAuth2 로그인 성공 후 가져올 설정들
                .userService(oAuth2DetailsService); // 서버에서 사용자 정보를 가져온 상태에서 추가로 진행하고자 하는 기능 명시




        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        return http.build();
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

//    @Bean(name = "templateEngine")
//    public SpringTemplateEngine getTemplateEngine() {
//        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
//
//        Set<ITemplateResolver> templatesResolvers = new HashSet<>();
//        templatesResolvers.add(getTemplateResolver());
//        templateEngine.addDialect(new LayoutDialect());
//        templateEngine.setTemplateResolvers(templatesResolvers);
//        templateEngine.addDialect(new SpringSecurityDialect());
//        return templateEngine;
//    }

}
