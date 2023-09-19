package webtoon.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.config.CustomUserDetails;
import webtoon.dto.episode.EpisodeFormDto;
import webtoon.dto.episode.EpisodeImgDto;
import webtoon.entity.member.Member;
import webtoon.repository.member.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class AdController {
    private final MemberRepository memberRepository;

    @GetMapping(value = "/ad/{adId}")
    public String episodeForm(@PathVariable("adId") Long adId, Model model) {
        String addImg = "";
        String title = "";
        String link = "";
        if (adId == 1) {
            addImg = "/images/cookie.png";
            title = "오픈 이벤트! 이미지를 클릭하고 쿠키 받으세요~";
            link = "/getCookie";
        } else if (adId == 2) {
            addImg = "/images/skyCode.png";
            title = "SkyCode 오픈";
            link = "#";
        } else if (adId == 3) {
            addImg = "/images/designer.png";
            title = "인덱스 웹툰 디자이너 구함";
            link = "#";
        } else if (adId == 4) {
            addImg = "/images/endCar.png";
            title = "End Car 오픈";
            link = "#";
        } else if (adId == 5) {
            addImg = "/images/writer.png";
            title = "우리는 누구나 웹툰을 올릴 수 있습니다!";
            link = "#";
        } else if (adId == 6) {
            addImg = "/images/developComment.png";
            title = "인덱스 웹툰 개발자 후기";
            link = "#";
        } else if (adId == 7) {
            addImg = "/images/teacher.png";
            title = "조휘일 & 허범회 선생님, 훌륭한 수업 감사했습니다.";
            link = "#";
        }

        model.addAttribute("addImg", addImg);
        model.addAttribute("title", title);
        model.addAttribute("link", link);
        return "ad/ad";
    }

    @GetMapping(value = "/getCookie")
    public String eventCookie(RedirectAttributes redirectAttributes) {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                // 로그인하지 않은 사용자에게는 예외 처리
                throw new NoSuchElementException("쿠키 이벤트는 로그인 회원만 받을 수 있습니다.");
            }
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String userEmail = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(userEmail);

            userId.setPoint(userId.getPoint() + 100);

            memberRepository.save(userId);

        } catch (NoSuchElementException e) {
            handleExceptionAndRedirect(e, redirectAttributes);

            return "redirect:/members/login";

        }
        redirectAttributes.addFlashAttribute("message", "쿠키를 선물로 드렸어요!");

        return "redirect:/main";
    }
    private void handleExceptionAndRedirect(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
    }

}
