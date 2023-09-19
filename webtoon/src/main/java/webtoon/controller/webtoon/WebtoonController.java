package webtoon.controller.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.api.TimeController;
import webtoon.config.CustomUserDetails;
import webtoon.dto.webtoon.*;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.WebtoonDayRepository;
import webtoon.repository.webtoon.WebtoonRepository;
//import webtoon.repository.webtoon.WebtoonDayRepository;
import webtoon.service.webtoon.WebtoonService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class WebtoonController {
    private final WebtoonService webtoonService;
    private final WebtoonRepository webtoonRepository;
    private final WebtoonDayRepository dayRepository;
    private final TimeController timeController;
    private final MemberRepository memberRepository;

    @GetMapping(value = "/new/Webtoon")
    public String webtoonForm(Model model) {
        model.addAttribute("webtoonFormDto", new WebtoonFormDto());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "로그인 회원만 작품 등록이 가능합니다.");

            return "member/memberLoginForm";
        }
        return "webtoon/webtoonForm";
    }

    @PostMapping(value = "/createWebtoon")
    public String newWebtoon(@Valid WebtoonFormDto webtoonFormDto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "로그인 회원만 작품 등록이 가능합니다.");

            return "member/memberLoginForm";
        }

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);

        if (bindingResult.hasErrors()) {
            return "webtoon/webtoonForm";
        }
        if (webtoonFormDto.getThumbnail1().isEmpty()) {
            model.addAttribute("errorMessage", "썸네일 가로 사진을 등록해주세요.");
            return "webtoon/webtoonForm";
        }
        if (webtoonFormDto.getThumbnail2().isEmpty()) {
            model.addAttribute("errorMessage", "썸네일 세로 사진을 등록해주세요.");
            return "webtoon/webtoonForm";
        }
        try {
            webtoonFormDto.setId(userId.getId());
            webtoonService.saveWebtoon(webtoonFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "웹툰 등록중 기타 에러발생");
            return "webtoon/webtoonForm";
        }
        redirectAttributes.addFlashAttribute("message", "작품이 둥록되었습니다.");
        return "redirect:/main/myPage";
    }


    @GetMapping("/dailyWebtoon")
    public String webtoonList(Model model) {
//        오늘 무슨 요일인지 알려주는 거 숫자로 보내줌
        int today = timeController.getServerDay();
        model.addAttribute("today", today);

        List<Webtoon> lastThreeWebtoons = webtoonService.getLastThreeWebtoons();
        if (lastThreeWebtoons.size() >= 3) {
            model.addAttribute("lastThreeWebtoons", lastThreeWebtoons);

        } else {
            model.addAttribute("lastThreeWebtoons", null);

        }

        List<Webtoon> mondayWebtoonList = dayRepository.findWebtoonsWithDayByView("월요일");
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(mondayWebtoonList);

        List<Webtoon> tuesdayWebtoonList = dayRepository.findWebtoonsWithDayByView("화요일");
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(tuesdayWebtoonList);

        List<Webtoon> wednesdayWebtoonList = dayRepository.findWebtoonsWithDayByView("수요일");
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(wednesdayWebtoonList);

        List<Webtoon> thursdayWebtoonList = dayRepository.findWebtoonsWithDayByView("목요일");
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(thursdayWebtoonList);

        List<Webtoon> fridayWebtoonList = dayRepository.findWebtoonsWithDayByView("금요일");
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(fridayWebtoonList);

        List<Webtoon> saturdayWebtoonList = dayRepository.findWebtoonsWithDayByView("토요일");
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(saturdayWebtoonList);

        List<Webtoon> sundayWebtoonList = dayRepository.findWebtoonsWithDayByView("일요일");
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(sundayWebtoonList);

        model.addAttribute("mondayWebtoons", mondayWebtoonDto);
        model.addAttribute("tuesdayWebtoons", tuesdayWebtoonDto);
        model.addAttribute("wednesdayWebtoons", wednesdayWebtoonDto);
        model.addAttribute("thursdayWebtoons", thursdayWebtoonDto);
        model.addAttribute("fridayWebtoons", fridayWebtoonDto);
        model.addAttribute("saturdayWebtoons", saturdayWebtoonDto);
        model.addAttribute("sundayWebtoons", sundayWebtoonDto);
        return "main/dailyWebtoon";
    }

    //    웹툰 유저 페이지
    @GetMapping(value = "/main/myPage/{webtoonId}")
    public String webtoonUserPage(@PathVariable("webtoonId") Long webtoonId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                throw new NoSuchElementException("비정상적인 접근입니다.");
            }

            Webtoon webtoonCheck = webtoonRepository.findById(webtoonId)
                    .orElseThrow(() -> new NoSuchElementException("해당 ID로 웹툰을 찾을 수 없습니다."));

            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String info = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(info);
            Webtoon webtoons = webtoonRepository.findById(webtoonId).orElseThrow();
//            로그인 한 사람이랑, 수정하는 작품 주인이랑 같지 않으면 잡음

            if (!(userId.getId().equals(webtoons.getMember().getId()))) {

                throw new NoSuchElementException("작품 회차는 해당 작가만 수정 가능합니다.");

            }

            WebtoonDetailDto webtoonData = webtoonService.getWebtoonDetailForm(webtoonCheck);
            List<WebtoonDetailImgDto> webtoonImgData = webtoonService.getWebtoonDetailImgForm(webtoonId);

            model.addAttribute("webtoonData", webtoonData);
//            역순 정렬 / 웹툰 1화가 밑으로 가게
            Collections.reverse(webtoonImgData);
            model.addAttribute("webtoonImgData", webtoonImgData);

            String successMessage = (String) redirectAttributes.getFlashAttributes().get("successMessage");
            if (successMessage != null && !successMessage.isEmpty()) {
                model.addAttribute("successMessage", successMessage);
            }

            return "webtoon/webtoonUserPage";
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "main/main";
        }
    }

    //    웹툰 상세 페이지
    @GetMapping(value = "/webtoonPage/{webtoonId}")
    public String webtoonPage(@PathVariable("webtoonId") Long webtoonId, Model model, RedirectAttributes redirectAttributes) {
        try {
            Webtoon webtoonCheck = webtoonRepository.findById(webtoonId)
                    .orElseThrow(() -> new NoSuchElementException("존재하지 않는 웹툰입니다"));


            WebtoonDetailDto webtoonData = webtoonService.getWebtoonDetailForm(webtoonCheck);
            List<WebtoonDetailImgDto> webtoonImgData = webtoonService.getWebtoonDetailImgForm(webtoonId);

            model.addAttribute("webtoonData", webtoonData);
//            역순 정렬 / 웹툰 1화가 밑으로 가게
            Collections.reverse(webtoonImgData);
            model.addAttribute("webtoonImgData", webtoonImgData);

            String successMessage = (String) redirectAttributes.getFlashAttributes().get("successMessage");
            String errorMessage = (String) redirectAttributes.getFlashAttributes().get("errorMessage");

            if (successMessage != null && !successMessage.isEmpty()) {
                model.addAttribute("successMessage", successMessage);
            }
            if (errorMessage != null && !errorMessage.isEmpty()) {
                model.addAttribute("errorMessage", errorMessage);
            }

            return "webtoon/webtoonPage";
        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "main/main";
        }
    }

    @GetMapping(value = "/edit/webtoon/{webtoonId}")
    public String EditWebtoonForm(@PathVariable("webtoonId") Long webtoonId, Model model) {


        try {

//            로그인 안 한 사람 잡기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                throw new NoSuchElementException("비정상적인 접근입니다.");
            }

//            웹툰이 없으면 반환
            Webtoon webtoon = webtoonRepository.findById(webtoonId)
                    .orElseThrow(() -> new NoSuchElementException("해당 ID로 웹툰을 찾을 수 없습니다."));


            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String info = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(info);
            Webtoon webtoons = webtoonRepository.findById(webtoonId).orElseThrow();
//            로그인 한 사람이랑, 수정하는 작품 주인이랑 같지 않으면 잡음
            if (!(userId.getId().equals(webtoons.getMember().getId()))) {
                throw new NoSuchElementException("작품 수정은 해당 작가만 수정 가능합니다.");

            }


            WebtoonEditDto webtoonEditDto = new WebtoonEditDto();
            webtoonEditDto.setId(webtoon.getId());
            webtoonEditDto.setTitle(webtoon.getTitle());
            webtoonEditDto.setWebtoonInfo(webtoon.getWebtoonInfo());
            webtoonEditDto.setDay(webtoon.getDay());
            webtoonEditDto.setGenre(webtoon.getGenre());
            webtoonEditDto.setAge_limit(webtoon.getAge_limit());
            webtoonEditDto.setThum1Link(webtoon.getThumbnail1());
            webtoonEditDto.setThum2Link(webtoon.getThumbnail2());
            webtoonEditDto.setWebtoonPath(webtoon.getWebtoonPath());
            model.addAttribute("webtoonEditDto", webtoonEditDto);

        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "main/main";
        }

        return "webtoon/webtoonEditForm";
    }


    @PostMapping(value = "/editWebtoon")
    public String EditWebtoon(@Valid WebtoonEditDto webtoonEditDto, Model model, RedirectAttributes redirectAttributes) {

        try {
            webtoonService.editWebtoon(webtoonEditDto);
            redirectAttributes.addFlashAttribute("message", "작품이 수정되었습니다.");
            return "redirect:/main/myPage";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생했습니다.");
        return "redirect:/main";
        }
    }

    @PostMapping("/deleteWebtoon")
    public String deleteWebtoon(@RequestParam("webtoonId") Long webtoonId, Model model, RedirectAttributes redirectAttributes) {

        try {
            Webtoon deleteWebtoon = webtoonRepository.findById(webtoonId)
                    .orElseThrow(() -> new NoSuchElementException("해당 ID로 웹툰을 찾을 수 없습니다."));

            webtoonRepository.delete(deleteWebtoon);

            redirectAttributes.addFlashAttribute("message", "작품이 삭제되었습니다.");
            return "redirect:/main/myPage";

        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", e.getMessage());
            if (e.getMessage().isEmpty()) {
                model.addAttribute("errorMessage", "기타 오류 발생");
            }
            return "main/main";
        }
    }

    @GetMapping("/dayWebtoon")
    public String showWebtoonPageByDay(@RequestParam("day") String day, Model model) {
        List<Webtoon> webtoonList = new ArrayList<>();
        List<WebtoonDayDto> dayWebtoons = new ArrayList<>();
        int num; // 기본값 설정

        if ("monday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("월요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 0;
        } else if ("tuesday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("화요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 1;
        } else if ("wednesday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("수요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 2;
        } else if ("thursday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("목요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 3;
        } else if ("friday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("금요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 4;
        } else if ("saturday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("토요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 5;
        } else if ("sunday".equals(day)) {
            webtoonList = dayRepository.findWebtoonsWithDayByView("일요일");
            dayWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
            num = 6;
        } else {
            return "main/main";
        }

        model.addAttribute("allWebtoon", dayWebtoons);
        model.addAttribute("num", num);

        return "main/dayWebtoon";
    }

}