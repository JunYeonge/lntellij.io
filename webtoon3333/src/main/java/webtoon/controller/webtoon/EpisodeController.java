package webtoon.controller.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.api.TimeController;
import webtoon.config.CustomUserDetails;
import webtoon.dto.episode.*;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.episodes.*;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;
import webtoon.entity.webtoon.WebtoonData;
import webtoon.repository.episodes.*;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.PurchaseHistoryRepository;
import webtoon.repository.webtoon.WebtoonDataRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.episode.EpisodeImgService;
import webtoon.service.episode.EpisodeService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class EpisodeController {
    private final EpisodeService episodeService;
    private final EpisodeImgService episodeImgService;
    private final WebtoonEpisodesRepository episodesRepository;
    private final MemberRepository memberRepository;
    private final EpisodeImgRepository episodeImgRepository;
    private final WebtoonRepository webtoonRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final TimeController timeController;
    private final StarRecordRepository starRecordRepository;
    private final WebtoonDataRepository webtoonDataRepository;
    private final LikeRecordRepository likeRecordRepository;
    private final EpisodeCommentRepository episodeCommentRepository;

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
            return "redirect:/new/episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("회차명을 입력하세요.", StandardCharsets.UTF_8);


        }

        if (episodeImgDto.getThumbnail().isEmpty()) {
            return "redirect:/new/episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("회차 썸네일 사진을 등록해주세요.", StandardCharsets.UTF_8);

        }

        if (episodeImgDto.getWebtoonImgPath().isEmpty()) {
            return "redirect:/new/episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("원고가 등록되지 않았습니다.", StandardCharsets.UTF_8);

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
            return "redirect:/new/episode/" + episodeFormDto.getWebtoonId() + "?errorMessage=" + URLEncoder.encode("회차 등록 중에 오류가 발생했습니다.", StandardCharsets.UTF_8);
        }
    }


    @GetMapping(value = "/webtoonPage/{webtoonId}/episode/{episodeId}")
    public String episodeForm(@RequestParam(name = "message", required = false) String message, @PathVariable("webtoonId") Long webtoonId, @PathVariable("episodeId") Long episodeId, Model model,
                              RedirectAttributes redirectAttributes, HttpServletResponse response, HttpServletRequest request) {
        try {
//            작품 유무
            Webtoon thisWebtoon = webtoonRepository.findById(webtoonId)
                    .orElseThrow(() -> new NoSuchElementException("작품을 찾을 수 없습니다."));
//            에피소드 유무
            WebtoonEpisodes thisEpisode = episodesRepository.findById(episodeId)
                    .orElseThrow(() -> new NoSuchElementException("에피소드를 찾을 수 없습니다."));

//            로그인 유무 확인
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if ((authentication == null || authentication instanceof AnonymousAuthenticationToken) && thisEpisode.getEpisodePoint() != 0) {
                // 로그인하지 않은 사용자에게는 예외 처리
                throw new NoSuchElementException("유료웹툰은 로그인 회원만 이용 가능합니다.");
            }

//            에피소드 내용 출력
            EpisodeImgs thisEpisodeImg = thisEpisode.getEpisodeImgsList().get(0);

//            쿠키 생성 및 유무 확인
            String postIdCookieName = "postView_" + thisEpisode.getId();
            Cookie[] cookies = request.getCookies();
            boolean isCookieFound = false;

            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals(postIdCookieName)) {
                        isCookieFound = true;
                        break;
                    }
                }
            }

            // 해당 게시글의 쿠키가 없을 때
            if (!isCookieFound) {
//                쿠키가 없어야 조회수 증가. 중복방지
                thisEpisode.setEpisodeView_count(thisEpisode.getEpisodeView_count() + 1);
//                로그인 한 유저라면 성별 카운트를 가져옴
                if (authentication != null) {
                    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
                    String userEmail = userDetails.getEmail();
                    Member userId = memberRepository.findByEmail(userEmail);
                    String gender = userId.getGender();
                    if (gender.equals("male")) {
                        thisEpisode.setEpisodeMan_count(thisEpisode.getEpisodeMan_count() + 1);
                    } else if (gender.equals("female")) {
                        thisEpisode.setEpisodeGirl_count(thisEpisode.getEpisodeGirl_count() + 1);
                    }
                }
                //  변경정보 저장
                episodesRepository.save(thisEpisode);


                int totalEpisodeViewCount = episodesRepository.sumEpisodeViewCountByWebtoonId(thisWebtoon.getId());
                int totalManCount = episodesRepository.sumEpisodeManCountByWebtoonId(thisWebtoon.getId());
                int totalGirlCount = episodesRepository.sumEpisodeGirlCountByWebtoonId(thisWebtoon.getId());

                // 웹툰 데이터 엔티티 가져오기
                WebtoonData webtoonData = webtoonDataRepository.findByWebtoonId(thisWebtoon.getId());
                // 조회수 업데이트
                webtoonData.setView_count(totalEpisodeViewCount);
                webtoonData.setMan_count(totalManCount);
                webtoonData.setGirl_count(totalGirlCount);

                // 웹툰 데이터 엔티티 저장
                webtoonDataRepository.save(webtoonData);


                Cookie cookie = new Cookie(postIdCookieName, "viewed"); // 게시글 쿠키 생성
                cookie.setMaxAge(24 * 60 * 60); // 쿠키 유효 시간 설정 (1일)
                cookie.setPath("/"); // 모든 경로에서 접근 가능
                response.addCookie(cookie); // 응답에 쿠키 추가
            }


            if (message != null) {
                model.addAttribute("message", message);
            }

            EpisodeCommentDto episodeCommentDto = new EpisodeCommentDto();
            episodeCommentDto.setEpisodeId(thisEpisode.getId());
            model.addAttribute("episodeCommentForm", episodeCommentDto);

            List<EpisodeComment> comments = episodeCommentRepository.findByWebtoonEpisodeId(thisEpisode.getId());
            List<EpisodeCommentDto> episodeCommentDtos = new ArrayList<>();

            for (EpisodeComment comment : comments) {
                EpisodeCommentDto dto = EpisodeCommentDto.fromEntity(comment);
                dto.setUser_name(comment.getNickname());
                episodeCommentDtos.add(dto);
            }
            Collections.reverse(episodeCommentDtos);
            model.addAttribute("episodeCommentList", episodeCommentDtos);

            if ((authentication == null || authentication instanceof AnonymousAuthenticationToken) && thisEpisode.getEpisodePoint() == 0) {
                // 비회원 무료 에피소드는 바로 페이지로 이동
                model.addAttribute("episodeViewDto", createEpisodeViewDto(thisEpisodeImg));
                return "/webtoon/episodePage";
            }

//            로그인 유저 정보 가져오기
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String userEmail = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(userEmail);

            StarRecord starRecord = starRecordRepository.findByUserIdAndEpisodeId(userId.getId(), thisEpisode.getId());
            LikeRecord likeRecord = likeRecordRepository.findByUserIdAndEpisodeId(userId.getId(), thisEpisode.getId());
//            별점을 준 적이 있는가.
            if (starRecord != null) {
                model.addAttribute("userStarRecode", starRecord.getUserStar());
            }
//            좋아요 준 적이 있는가.
            if (likeRecord != null) {
                model.addAttribute("userLikeRecode", "on");
            }

            // 작가 확인
            if (userId.getId().equals(thisWebtoon.getMember().getId())) {
                model.addAttribute("successMessage", userId.getNickname() + " 작가님 안녕하세요.");
                model.addAttribute("episodeViewDto", createEpisodeViewDto(thisEpisodeImg));
                return "/webtoon/episodePage";
            }

            if (thisEpisode.getEpisodePoint() == 0) {
                // 회원 무료 에피소드는 바로 페이지로 이동
                model.addAttribute("episodeViewDto", createEpisodeViewDto(thisEpisodeImg));
                return "/webtoon/episodePage";
            }

            PurchaseHistory userPurchaseHistory = purchaseHistoryRepository.findOneByMemberAndWebtoonEpisodes(userId, thisEpisode);

            if (userPurchaseHistory != null) {
                // 이미 구매한 경우 처리
                model.addAttribute("successMessage", "이미 구매한 작품입니다.");
                model.addAttribute("episodeViewDto", createEpisodeViewDto(thisEpisodeImg));
                return "/webtoon/episodePage";
            }

            int userPoint = userId.getPoint();
            int price = thisEpisode.getEpisodePoint();

            if (userPoint >= price) {
                // 포인트가 충분한 경우 구매 처리
                userId.setPoint(userPoint - price);
                memberRepository.save(userId);

                PurchaseHistory purchaseHistory = createPurchaseHistory(userId, thisEpisode, price);
                purchaseHistoryRepository.save(purchaseHistory);

                model.addAttribute("successMessage", "구매 완료.");
                model.addAttribute("episodeViewDto", createEpisodeViewDto(thisEpisodeImg));
                return "/webtoon/episodePage";
            } else {
                throw new NoSuchElementException("보유한 쿠키가 모자랍니다.");
            }

        } catch (NoSuchElementException e) {
            handleExceptionAndRedirect(e, redirectAttributes);
            if (e.getMessage().equals("유료웹툰은 로그인 회원만 이용 가능합니다.")) {
                return "redirect:/members/login";
            } else if(e.getMessage().equals("보유한 쿠키가 모자랍니다.")) {

                return "redirect:/payment/toss";
            }
            return "redirect:/webtoonPage/" + webtoonId;
        }
    }

    // EpisodeViewDto 생성
    private EpisodeViewDto createEpisodeViewDto(EpisodeImgs episodeImg) {
        EpisodeViewDto episodeViewDto = new EpisodeViewDto();
        episodeViewDto.setId(episodeImg.getId());
        episodeViewDto.setWebtoonPath(episodeImg.getWebtoonPath());
        episodeViewDto.setThumbnail(episodeImg.getThumbnail());
        episodeViewDto.setWebtoonImgPath(episodeImg.getWebtoonImgPath());
        return episodeViewDto;
    }

    // PurchaseHistory 생성
    private PurchaseHistory createPurchaseHistory(Member userId, WebtoonEpisodes episode, int price) {
        PurchaseHistory purchaseHistory = new PurchaseHistory();
        purchaseHistory.setPurchaseDay(timeController.getServerTime());
        purchaseHistory.setPurchasePrice(price);
        purchaseHistory.setWebtoonEpisodes(episode);
        purchaseHistory.setMember(userId);
        return purchaseHistory;
    }

    // 예외 처리 및 리다이렉션
    private void handleExceptionAndRedirect(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
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
