package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.api.TimeController;
import webtoon.config.CustomUserDetails;
import webtoon.dto.*;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.episodes.EpisodeImgs;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.episodes.EpisodeImgRepository;
import webtoon.repository.episodes.WebtoonEpisodesRepository;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.PurchaseHistoryRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.EpisodeImgService;
import webtoon.service.EpisodeService;
import webtoon.service.WebtoonService;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class EpisodeController {
    private final WebtoonService webtoonService;
    private final EpisodeService episodeService;
    private final EpisodeImgService episodeImgService;
    private final WebtoonEpisodesRepository episodesRepository;
    private final MemberRepository memberRepository;
    private final EpisodeImgRepository episodeImgRepository;
    private final WebtoonRepository webtoonRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final TimeController timeController;
    @GetMapping(value = "/new/episode/{webtoonId}")
    public String episodeForm(@PathVariable("webtoonId") Long webtoonId, @RequestParam(name = "errorMessage", required = false) String errorMessage, Model model) {
        if (errorMessage != null) {
            model.addAttribute("errorMessage", errorMessage);
        }
        EpisodeFormDto episodeFormDto = new EpisodeFormDto();
        EpisodeImgDto episodeImgDto = new EpisodeImgDto();
        episodeFormDto.setWebtoonId(webtoonId);
        model.addAttribute("episodeFormDto", episodeFormDto);
        model.addAttribute("episodeImgDto", episodeImgDto);
        return "/webtoon/webtoonEpisodeForm";
    }

    @PostMapping(value = "/createEpisode")
    public String createEpisode(@Valid EpisodeFormDto episodeFormDto, @Valid EpisodeImgDto episodeImgDto, Model model, RedirectAttributes redirectAttributes) {

        if (episodeFormDto.getTitle().isEmpty()) {
            return "redirect:/new/Episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("회차명을 입력하세요.", StandardCharsets.UTF_8);

        }

        if (episodeImgDto.getThumbnail().isEmpty()) {
            return "redirect:/new/Episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("회차 썸네일 사진을 등록해주세요.", StandardCharsets.UTF_8);

        }

        if (episodeImgDto.getWebtoonImgPath().isEmpty()) {
            return "redirect:/new/Episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("원고가 등록되지 않았습니다.", StandardCharsets.UTF_8);

        }

        try {
            // 회차 등록
            Long createEpisode = episodeService.saveEpisode(episodeFormDto);

            // 회차 이미지 등록
            episodeImgDto.setEpisodeId(createEpisode);
            episodeImgService.saveEpisodeImg(episodeImgDto);
            WebtoonEpisodes epWebtoon = episodesRepository.findById(createEpisode).orElseThrow();
//            성공 메시지
            redirectAttributes.addFlashAttribute("successMessage", "회차가 등록되었습니다.");
            return "redirect:/main/myPage/" + epWebtoon.getWebtoon().getId();
        } catch (Exception e) {
            return "redirect:/new/Episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("회차 등록 중에 오류가 발생했습니다.", StandardCharsets.UTF_8);
        }
    }


    @GetMapping(value = "/webtoonPage/{webtoonId}/episode/{episodeId}")
    public String episodeForm(@PathVariable("webtoonId") Long webtoonId, @PathVariable("episodeId") Long episodeId, Model model, RedirectAttributes redirectAttributes) {
        Webtoon thisWebtoon = webtoonRepository.findById(webtoonId)
                .orElseThrow(() -> new NoSuchElementException("작품을 찾을 수 없습니다."));

        WebtoonEpisodes thisEpisode = episodesRepository.findById(episodeId)
                .orElseThrow(() -> new NoSuchElementException("에피소드를 찾을 수 없습니다."));
        try {


            if (thisEpisode.getEpisodePoint() == 0) {

                return "/webtoon/episodePage";

            } else {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                    throw new NoSuchElementException("유료웹툰은 로그인 회원만 이용 가능합니다.");

                }
//                로그인한 사람 정보
                CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                String info = userDetails.getEmail();
                Member userId = memberRepository.findByEmail(info);
                System.out.println("help2");

                PurchaseHistory userPurchaseHistory = purchaseHistoryRepository.findOneByMemberAndWebtoonEpisodes(userId, thisEpisode);

                if(userPurchaseHistory != null) {
                    model.addAttribute("successMessage", "이미 구매했음.");
                    return "/webtoon/episodePage";
                }
                int userPoint = userId.getPoint();
                int price = thisEpisode.getEpisodePoint();
                if (userPoint >= price) {
                    userId.setPoint(userPoint-price);
                    memberRepository.save(userId);
                    PurchaseHistory purchaseHistory = new PurchaseHistory();

                    purchaseHistory.setPurchaseDay(timeController.getServerTime());
                    purchaseHistory.setPurchasePrice(price);
                    purchaseHistory.setWebtoonEpisodes(thisEpisode);
                    purchaseHistory.setMember(userId);

                    purchaseHistoryRepository.save(purchaseHistory);

//                    이제 뿌려주는 로직 짜면 됨
                    model.addAttribute("successMessage", "구매 완료.");
                    return "/webtoon/episodePage";

                } else {
                    throw new NoSuchElementException("보유한 쿠키가 모자랍니다.");
                }
            }
        } catch (NoSuchElementException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            if (e.getMessage().equals("유료웹툰은 로그인 회원만 이용 가능합니다.")) {
                return "redirect:/members/login";
            }
            if (e.getMessage().equals("보유한 쿠키가 모자랍니다.")) {
//                결제창 만들면 거기로 이동
                return "redirect:/members/login";
            }

            return "redirect:/webtoonPage/" + webtoonId;
        }


    }


    @GetMapping(value = "/edit/episode/{episodeId}")
    public String EditEpisodeForm(@PathVariable("episodeId") Long episodeId, Model model) {


        try {

//            로그인 안 한 사람 잡기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
                throw new NoSuchElementException("비정상적인 접근입니다.");
            }

//            웹툰이 없으면 반환
            WebtoonEpisodes episode = episodesRepository.findById(episodeId)
                    .orElseThrow(() -> new NoSuchElementException("해당 ID로 에피소드를 찾을 수 없습니다."));


            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String info = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(info);
            WebtoonEpisodes episodes = episodesRepository.findById(episodeId).orElseThrow();
//            로그인 한 사람이랑, 수정하는 작품 주인이랑 같지 않으면 잡음
            if (!(userId.getId().equals(episodes.getWebtoon().getMember().getId()))) {
                throw new NoSuchElementException("작품 수정은 해당 작가만 수정 가능합니다.");

            }
            EpisodeEditDto episodeEditDto = new EpisodeEditDto();
            EpisodeImgEditDto episodeImgEditDto = new EpisodeImgEditDto();

            episodeEditDto.setId(episodes.getId());
            episodeEditDto.setPrice(episodes.getEpisodePoint());
            episodeEditDto.setWebtoonId(episodes.getWebtoon().getId());
            episodeEditDto.setTitle(episodes.getEpisodeTitle());

            EpisodeImgs episodeImgs = episodeImgRepository.findByWebtoonEpisodeId(episodes.getId()).get(0);

            episodeImgEditDto.setEpisodeId(episodes.getId());
            episodeImgEditDto.setId(episodeImgs.getId());
            episodeImgEditDto.setWebtoonLink(episodeImgs.getWebtoonPath());
            episodeImgEditDto.setWebtoonImgLink(episodeImgs.getWebtoonImgPath());
            episodeImgEditDto.setThumbnailLink(episodeImgs.getThumbnail());

            model.addAttribute("episodeEditDto", episodeEditDto);
            model.addAttribute("episodeImgEditDto", episodeImgEditDto);

        } catch (NoSuchElementException e) {
            model.addAttribute("errorMessage", e.getMessage());
            System.out.println(e.getMessage());
            return "/main/main";
        }

        return "/webtoon/episodeEditForm";
    }


    @PostMapping(value = "/editEpisode")
    public String EditWebtoon(@Valid EpisodeEditDto episodeEditDto, @Valid EpisodeImgEditDto episodeImgEditDto, Model model, RedirectAttributes redirectAttributes) {

        try {
            episodeService.editEpisode(episodeEditDto, episodeImgEditDto);

            WebtoonEpisodes epWebtoon = episodesRepository.findById(episodeEditDto.getId())
                    .orElseThrow(() -> new NoSuchElementException("해당 ID로 웹툰을 찾을 수 없습니다."));


            redirectAttributes.addFlashAttribute("successMessage", "회차가 수정되었습니다.");
            return "redirect:/main/myPage/" + epWebtoon.getWebtoon().getId();
        } catch (
                Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생했습니다.");
        }

        return "redirect:/main";

    }

}
