package com.bed.service;

import com.bed.config.CustomUserDetails;
import com.bed.dto.MemberEditFormDto;
import com.bed.entity.Address;
import com.bed.entity.Member;
import com.bed.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.thymeleaf.util.StringUtils;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Transactional
//@RequiredArgsConstructor
public class  MemberService implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;

    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }


    public Member saveMember(Member member) {
        validateDupulicateMember(member);

        // 닉네임이 비어있을 경우 이메일의 '@' 앞 부분을 사용
        if (StringUtils.isEmpty(member.getNickname())) {
            String email = member.getEmail();
            int atIndex = email.indexOf("@");
            if (atIndex != -1) {
                String nickname = email.substring(0, atIndex);
                member.setNickname(nickname);
            }
        }
        return memberRepository.save(member);
    }


    public boolean isPhoneNumberExists(String phoneNumber) {
        Member findMember = memberRepository.findByPhoneNumber(phoneNumber);
        return findMember != null;
    }



    public void updateMember(Authentication authentication,
                             MemberEditFormDto memberEditFormDto,
                             BindingResult bindingResult) {
        String email = authentication.getName();

        Member member = memberRepository.findByEmail(email);
        if (member != null) {
            boolean nicknameChanged = false;
            if (!memberEditFormDto.getNickname().isEmpty()) {
                member.setNickname(memberEditFormDto.getNickname());
                nicknameChanged = true;
            }
            if (!memberEditFormDto.getPhoneNumber().isEmpty()) {
                member.setPhoneNumber(memberEditFormDto.getPhoneNumber());
            }
            if (memberEditFormDto.getAddress() != null) {
                Address address = member.getAddress();
                address.setPostcode(memberEditFormDto.getAddress().getPostcode());
                address.setAddress(memberEditFormDto.getAddress().getAddress());
                address.setDetailAddress(memberEditFormDto.getAddress().getDetailAddress());
                address.setExtraAddress(memberEditFormDto.getAddress().getExtraAddress());
                member.setAddress(address);
                member.setMerge_address(address.getPostcode() + " " + address.getAddress() + " " + address.getDetailAddress() + " " + address.getExtraAddress());
            }
            if (!memberEditFormDto.getNewPassword().isEmpty()) {
                if (!memberEditFormDto.getNewPassword().equals(memberEditFormDto.getConfirmPassword())) {
                    bindingResult.rejectValue("confirmPassword", "newPassword.mismatch", "비밀번호가 일치하지 않습니다.");
                    return;
                }
                if (passwordPatternIsValid(memberEditFormDto.getNewPassword())) {
                    member.setPassword(passwordEncoder.encode(memberEditFormDto.getNewPassword()));
                } else {
                    bindingResult.rejectValue("newPassword", "passwordPattern.invalid", "비밀번호는 8~16자, 숫자, 특수문자를 사용하세요.");
                    return;
                }
            }
            if (authentication instanceof AnonymousAuthenticationToken) {
                member.setModifiedBy("anonymousUser");
            } else {
                member.setModifiedBy(email);
            }

            memberRepository.save(member);

            if(nicknameChanged){
                refreshUserSession(email);
            }
        } else {
            throw new IllegalArgumentException("회원 정보를 찾을 수 없습니다.");
        }
    }

//    public void deactivateMember(String email, String password, Member member) {
////        Member member = memberRepository.findByEmail(email);
//        if (passwordEncoder.matches(password, member.getPassword())) {
//            member.setActive(false);
//            member.setDeactivatedAt(LocalDateTime.now());  // 탈퇴 시간 설정
//            memberRepository.save(member);
//        } else {
//            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
//        }
//    }

    public void deactivateMember(String email, String password, Member member) {
        if (passwordEncoder.matches(password, member.getPassword())) {
            memberRepository.delete(member);
        } else {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }
    }

    public void refreshUserSession(String email) {
        UserDetails userDetails = this.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken newAuth =
                new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuth);
    }

    // 패턴 검증 메서드
    private boolean passwordPatternIsValid(String password) {
        // 비밀번호는 8~16자이고, 숫자와 특수 문자를 포함해야 합니다.
        String passwordPattern = "^(?=.*\\d)(?=.*[~`!@#$%\\^&*()-])(?=.*[a-zA-Z]).{8,16}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }


    private void validateDupulicateMember(Member member) {
        Member findMember = memberRepository.findByEmail(member.getEmail());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
        if (isPhoneNumberExists(member.getPhoneNumber())) {
            throw new IllegalStateException("이미 사용 중인 전화번호입니다.");
        }
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws
            UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member != null) {
            return new CustomUserDetails(member);
        } else {
            throw new UsernameNotFoundException("회원을 찾을 수 없습니다: " + email);

        }

    }


}
