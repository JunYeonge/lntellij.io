package webtoon.controller.member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;
import webtoon.config.CustomUserDetails;
import webtoon.dto.member.AddressFormDto;
import webtoon.dto.member.MemberEditFormDto;
import webtoon.dto.member.MemberFormDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;
import webtoon.service.member.MemberService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }
        if (!memberFormDto.getPassword().equals(memberFormDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword",
                    "password.mismatch", "비밀번호가 일치하지 않습니다.");
            return "member/memberForm";
        }
        Member member = Member.createMember(memberFormDto, passwordEncoder);

        // 닉네임 충돌 방지
        if (StringUtils.isEmpty(memberFormDto.getNickname())) {
            // email에서 @ 이전의 부분을 추출하여 nickname으로 저장
            String[] emailParts = memberFormDto.getEmail().split("@");
            member.setNickname(emailParts[0]);
        } else {
            member.setNickname(memberFormDto.getNickname());
        }

        // 주민등록번호 설정
        member.setResidentIdFront(memberFormDto.getResidentIdFront());
        member.setResidentIdBack(memberFormDto.getResidentIdBack());

        try {
            memberService.saveMember(member);
            // 회원가입이 성공한 경우, JavaScript로 알림을 표시하기 위한 변수를 설정합니다.
            model.addAttribute("showSuccessAlert", true);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
        }

        // 리다이렉트 URL을 반환하지 않고, 템플릿에서 자바스크립트로 리다이렉트합니다.
        return "member/memberForm";
    }


    @GetMapping(value = "/login")
    public String loginMember(Model model, RedirectAttributes redirectAttributes){


        String successMessage = (String) redirectAttributes.getFlashAttributes().get("successMessage");
        String errorMessage = (String) redirectAttributes.getFlashAttributes().get("errorMessage");

        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }
        if (errorMessage != null && !errorMessage.isEmpty()) {
            model.addAttribute("errorMessage", errorMessage);
        }
        model.addAttribute("headerImg","/images/loginHeader.png");
        return "member/memberLoginForm";
    }

    @GetMapping(value = "/login/error")
    public String loginError(Model model){
        model.addAttribute("loginErrorMsg","아이디 또는 비밀번호를 확인해주세요");
        return "member/memberLoginForm";
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

    //회원 정보 수정 폼을 보여주는 핸들러

    @GetMapping("/edit")
    public String showEditForm(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        Member member = memberService.getMemberByEmail(email);
        String nickname = member.getNickname();
        MemberEditFormDto memberEditFormDto = new MemberEditFormDto();
        memberEditFormDto.setEmail(member.getEmail());
        memberEditFormDto.setNickname(member.getNickname());
        AddressFormDto addressFormDto = new AddressFormDto();
        addressFormDto.setAddress(member.getAddress());
        memberEditFormDto.setAddress(addressFormDto);
        model.addAttribute("memberEditFormDto", memberEditFormDto);
        model.addAttribute("nickname", nickname);

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
            // 정보 수정 성공 시 JavaScript로 알림창 표시
            String successMessage = "정보 수정이 완료되었습니다.";
            model.addAttribute("successMessage", successMessage);
            model.addAttribute("showAlert", true); // showAlert를 추가하여 Thymeleaf에서 알림창을 표시할 것임을 지정
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
        return "redirect:/main/myPage";
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