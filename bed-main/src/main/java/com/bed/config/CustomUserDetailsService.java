package com.bed.config;

import com.bed.entity.Member;
import com.bed.repository.MemberRepository;
import com.bed.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        CustomUserDetails userDetails = new CustomUserDetails(member);
        userDetails.setAuthenticated(true); // 인증을 받았다고 설정
        userDetails.setNickname(member.getNickname()); // 닉네임 설정

        return userDetails;
        // 사용자 정보에 닉네임 포함
//        return new CustomUserDetails(member.getEmail(), member.getPassword(), member.getNickname(), member.getRole(), member.getName() , member.getPhoneNumber());
//        return new CustomUserDetails(member);

    }
}
