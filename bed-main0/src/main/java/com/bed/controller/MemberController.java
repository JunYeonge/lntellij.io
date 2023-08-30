package com.bed.controller;


import com.bed.config.CustomUserDetails;
import com.bed.constant.Role;
import com.bed.dto.AddressFormDto;
import com.bed.dto.MemberEditFormDto;
import com.bed.dto.MemberFormDto;
import com.bed.entity.Address;
import com.bed.entity.Member;
import com.bed.repository.MemberRepository;
import com.bed.service.MemberService;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@RequestMapping("/members")
@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/new")
    public String memberForm(Model model){
        model.addAttribute("memberFormDto", new MemberFormDto());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(@Valid MemberFormDto memberFormDto,
                             BindingResult bindingResult, Model model,
                             Authentication authentication){
        if(bindingResult.hasErrors()){
            return "member/memberForm";
        }
        if (!memberFormDto.getPassword().equals(memberFormDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword",
                    "password.mismatch", "비밀번호가 일치하지 않습니다.");
            return "member/memberForm";
        }
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        if(StringUtils.isEmpty(memberFormDto.getPhoneNumber())){
            memberFormDto.setPhoneNumber(null);
        }else{
            if(memberService.isPhoneNumberExists(memberFormDto.getPhoneNumber())){

                bindingResult.rejectValue("phoneNumber", "phoneNumber.exists",
                        "이미 사용 중인 전화번호입니다.");
                return "member/memberForm";
            }
        }
        // 닉네임 충돌 방지
        if (StringUtils.isEmpty(memberFormDto.getNickname())) {
            // email에서 @ 이전의 부분을 추출하여 nickname으로 저장
            String[] emailParts = memberFormDto.getEmail().split("@");
            member.setNickname(emailParts[0]);
        } else {
            member.setNickname(memberFormDto.getNickname());
        }
        try {
            memberService.saveMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }
        return "redirect:/";
    }

    @GetMapping(value = "/login")
    public String loginMember(Model model){

        model.addAttribute("headerImg","/images/loginHeader.png");
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
    }

    @GetMapping("/edit/password")
    public String showPasswordConfirmationPage(Model model) {
        model.addAttribute("headerImg","/images/header.jpg");
        return "member/passwordConfirmationForm";
    }

    @PostMapping("/edit/password")
    public String checkPassword(Authentication authentication, @RequestParam String password) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();

        if (passwordEncoder.matches(password, member.getPassword())) {
            return "redirect:/members/edit";
        } else {
            return "redirect:/members/edit/password?error";
        }
    }

        // 회원 정보 수정 폼을 보여주는 핸들러
        @GetMapping("/edit")
        public String showEditForm(Model model, Authentication authentication) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername(); // 사용자의 이메일을 가져옴
            Member member = memberService.getMemberByEmail(email); // 이메일을 기준으로 회원 정보 조회
            String nickname = member.getNickname();

            MemberEditFormDto memberEditFormDto = new MemberEditFormDto();
            memberEditFormDto.setEmail(member.getEmail());
            memberEditFormDto.setNickname(member.getNickname());
            memberEditFormDto.setPhoneNumber(member.getPhoneNumber());
            model.addAttribute("headerImg","/images/header.jpg");
            memberEditFormDto.setAddress(member.getAddress());  // convertToAddressFormDto 사용하지 않고 직접 설정


            model.addAttribute("memberEditFormDto", memberEditFormDto);
            model.addAttribute("nickname", nickname);  // 닉네임 추가

            return "member/editForm";
        }

        // 회원 정보 수정을 처리하는 핸들러
        @PostMapping("/edit")
        public String processEditForm(@ModelAttribute("memberEditFormDto") @Valid MemberEditFormDto memberEditFormDto,
                                      BindingResult bindingResult, Authentication authentication,
                                      Model model, HttpServletRequest request, HttpServletResponse response) {
            if (bindingResult.hasErrors()) {
                return "member/editForm";
            }

            try {
                memberService.updateMember(authentication, memberEditFormDto, bindingResult);
            } catch (IllegalStateException e) {
                model.addAttribute("errorMessage", e.getMessage());
                return "member/editForm";
            }
            if (bindingResult.hasErrors()) {
                return "member/editForm";
            }
            if (!memberEditFormDto.getNewPassword().isEmpty()) {
                if (!bindingResult.hasErrors()) {
                    new SecurityContextLogoutHandler().logout(request, response, authentication);
                    return "redirect:/members/login";
                } else {
                    return "member/editForm";
                }
            }
            return "redirect:/members/edit";
        }

    // 회원 탈퇴를 위한 비밀번호 확인 페이지
    // 탈퇴 폼을 보여주는 핸들러
    @GetMapping("/deactivate")
    public String showDeactivationForm(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();
        model.addAttribute("member", member);
        return "/member/deactivationForm";
    }

    // 회원 탈퇴 처리 핸들러
    @PostMapping("/deactivate")
    public String processDeactivation(@RequestParam(value = "password", required = false) String password,
                                      Authentication authentication,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {

        if (StringUtils.isEmpty(password)) {
            return "redirect:/members/deactivate?error";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Member member = userDetails.getMember();


        // 비밀번호 일치 여부 확인
        if (passwordEncoder.matches(password, member.getPassword())) {
            // 비밀번호가 일치하면 회원 탈퇴 처리
            memberService.deactivateMember(member.getEmail(), password, member);

            // 회원 탈퇴 후 로그아웃
            new SecurityContextLogoutHandler().logout(request, response, authentication);

            // 로그인 페이지로 리다이렉트
            return "redirect:/members/login";
        } else {
            // 비밀번호가 일치하지 않으면 에러 메시지와 함께 탈퇴 페이지로 리다이렉트
            return "redirect:/members/deactivate?error";
        }
    }


}
