package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.api.TimeController;

import javax.servlet.http.HttpServletRequest;

import webtoon.config.CustomUserDetails;
import webtoon.dto.WebtoonDayDto;
import webtoon.dto.WebtoonDetailDto;
import webtoon.dto.member.MemberInfoFormDto;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.WebtoonDayRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.WebtoonService;
import webtoon.service.member.MemberService;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor

public class MainController {
    private final MemberRepository memberRepository;
    private final WebtoonRepository webtoonRepository;
    private final WebtoonService webtoonService;
    private final MemberService memberService;
    private final TimeController timeController;
    private final WebtoonDayRepository dayRepository;

    @GetMapping(value = "/main")
    public String main(Model model) {

        int today = timeController.getServerDay();
        model.addAttribute("today", today);

        List<Webtoon> mondayWebtoonList = dayRepository.findWebtoonsWithMondayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Webtoon> tuesdayWebtoonList = dayRepository.findWebtoonsWithTuesdayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Webtoon> wednesdayWebtoonList = dayRepository.findWebtoonsWithWednesdayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Webtoon> thursdayWebtoonList = dayRepository.findWebtoonsWithThursdayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Webtoon> fridayWebtoonList = dayRepository.findWebtoonsWithFridayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Webtoon> saturdayWebtoonList = dayRepository.findWebtoonsWithSaturdayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        List<Webtoon> sundayWebtoonList = dayRepository.findWebtoonsWithSundayByView()
                .stream()
                .limit(5)
                .collect(Collectors.toList());



        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(mondayWebtoonList);
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(tuesdayWebtoonList);
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(wednesdayWebtoonList);
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(thursdayWebtoonList);
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(fridayWebtoonList);
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(saturdayWebtoonList);
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(sundayWebtoonList);

        model.addAttribute("mondayWebtoons", mondayWebtoonDto);
        model.addAttribute("tuesdayWebtoons", tuesdayWebtoonDto);
        model.addAttribute("wednesdayWebtoons", wednesdayWebtoonDto);
        model.addAttribute("thursdayWebtoons", thursdayWebtoonDto);
        model.addAttribute("fridayWebtoons", fridayWebtoonDto);
        model.addAttribute("saturdayWebtoons", saturdayWebtoonDto);
        model.addAttribute("sundayWebtoons", sundayWebtoonDto);

        return "main/main";
    }


    @GetMapping(value = "/main/allWebtoon")
    public String allwebtoon() {
//        그냥 이거 한 줄이면 됩니다. 뭐 받아올 필요도 없어요.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            System.out.println("errnotlogin");
            return "/main/allWebtoon";
        }
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String nickname = userDetails.getNickname();
        String phoneNumber = userDetails.getPhoneNumber();
        System.out.println("Nickname: " + nickname);
        System.out.println("Nickname: " + phoneNumber);
        System.out.println(authentication.getName());
        System.out.println("serr");
        return "main/allWebtoon";
    }

    @GetMapping(value = "/main/myPage")
    public String myPage(Model model, RedirectAttributes redirectAttributes) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "마이 페이지는 로그인 회원만 이용가능합니다.");
            return "/member/memberLoginForm";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);


        List<Webtoon> webtoonNull = webtoonRepository.findByMemberId(userId.getId());
        if (webtoonNull == null) {
            // 사용자 정보를 찾을 수 없는 경우 예외 처리
            model.addAttribute("errorMessage", "사용자 정보를 찾을 수 없습니다.");
            return "/main";
        }

        MemberInfoFormDto memberInfo = memberService.getUserInfo(userId.getId());
        List<WebtoonDetailDto> userWebtoonList = webtoonService.getUserWebtoon(userId.getId());

        String successMessage = (String) redirectAttributes.getFlashAttributes().get("successMessage");
        if (successMessage != null && !successMessage.isEmpty()) {
            model.addAttribute("successMessage", successMessage);
        }

        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("userWebtoonList", userWebtoonList);

        return "main/myPage";
    }

    @GetMapping(value = "/main/searchPage")
    public String searchPage() {

        return "main/search";
    }


}
