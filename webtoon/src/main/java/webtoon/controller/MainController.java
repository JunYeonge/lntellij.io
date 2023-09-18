package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.api.TimeController;

import webtoon.config.CustomUserDetails;
import webtoon.dto.board.BoardDto;
import webtoon.dto.member.MemberInfoFormDto;
import webtoon.dto.purchase.PurchaseDto;
import webtoon.dto.webtoon.WebtoonDayDto;
import webtoon.dto.webtoon.WebtoonDetailDto;
import webtoon.dto.webtoon.WebtoonInfoDto;
import webtoon.dto.webtoon.WebtoonViewCountDto;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.episodes.EpisodeImgs;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.PurchaseHistoryRepository;
import webtoon.repository.webtoon.WebtoonDayRepository;
import webtoon.repository.webtoon.WebtoonGenreRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.board.BoardService;
import webtoon.service.webtoon.WebtoonService;
import webtoon.service.member.MemberService;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor

public class MainController {
    private final MemberRepository memberRepository;
    private final WebtoonService webtoonService;
    private final MemberService memberService;
    private final TimeController timeController;
    private final WebtoonDayRepository dayRepository;
    private final WebtoonGenreRepository genreRepository;
    private final PurchaseHistoryRepository purchaseHistoryRepository;
    private final BoardService boardService;
    private final WebtoonRepository webtoonRepository;

    @GetMapping(value = {"/", "/main"})
    public String mainPage(Model model, Pageable pageable, @ModelAttribute("message") String message) {
//         시간 api 오늘 무슨 요일인지에 따라 출력
        int today = timeController.getServerDay();
        model.addAttribute("today", today);

        if (message != null) {
            model.addAttribute("message", message);
        }

//        요일 웹툰 출력
        for (DayOfWeek dayOfWeek : DayOfWeek.values()) {
            List<Webtoon> webtoonList = new ArrayList<>();

            switch (dayOfWeek) {
                case MONDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("월요일");
                    break;
                case TUESDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("화요일");
                    break;
                case WEDNESDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("수요일");
                    break;
                case THURSDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("목요일");
                    break;
                case FRIDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("금요일");
                    break;
                case SATURDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("토요일");
                    break;
                case SUNDAY:
                    webtoonList = dayRepository.findWebtoonsWithDayByView("일요일");
                    break;
            }

            List<Webtoon> limitedWebtoonList = webtoonList.stream()
                    .limit(5)
                    .collect(Collectors.toList());

            List<WebtoonDayDto> webtoonDtoList = webtoonService.getWebtoonDayDto(limitedWebtoonList);

            model.addAttribute(dayOfWeek.toString().toLowerCase() + "Webtoons", webtoonDtoList);
        }

//    화면에 처음 로맨스 장르 먼저 뿌려짐
        List<Webtoon> romanceWebtoonList = genreRepository.findAllByGenre("로맨스")
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("genreWebtoonDto", webtoonService.getWebtoonDayDto(romanceWebtoonList));

//    오른쪽 남성, 여성 조회순 정렬
        List<Webtoon> genderWebtoonList = genreRepository.findByGender("view_count")
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        model.addAttribute("genderWebtoonDto", webtoonService.getWebtoonDayDto(genderWebtoonList));

//        신작 순서 정렬
        List<Webtoon> newWebtoonList = webtoonRepository.findAllByOrderWebtoonData_RegistrationDateDesc()
                .stream()
                .limit(7)
                .collect(Collectors.toList());

        model.addAttribute("newWebtoonListDto", webtoonService.getWebtoonDayDto(newWebtoonList));


//        준영

        // 공지사항
        pageable = PageRequest.of(pageable.getPageNumber(), 7, Sort.by("id").descending());
        Page<BoardDto> boardDtoPage = boardService.findAll(pageable);
        model.addAttribute("boardTitles", boardDtoPage); // 제목만 모델에 추가합니다

        // 웹툰 랜덤 추천
        List<WebtoonViewCountDto> webtoonViewCountDto = webtoonService.getWebtoonRandomCount().stream()
                .limit(5)
                .collect(Collectors.toList());
        model.addAttribute("webtoonDtos", webtoonViewCountDto);

        //이달의 신작
        List<WebtoonInfoDto> webtoonInfoList = webtoonService.getWebtoonsByRegistrationDate().stream()
                .limit(3) // 최대 3개만 가져오도록 제한
                .collect(Collectors.toList());
        model.addAttribute("webtoonInfoList", webtoonInfoList);

        return "main/main";
    }

    @GetMapping("/webtoon")
    @SuppressWarnings("unchecked")
    public String showWebtoonPageByGenre(@RequestParam("genre") String genre, Model model) {
//        웹툰 리스트 반환될 객체
        List<WebtoonDayDto> generWebtoons = null;
//        뷰에 버튼 색 순서
        int num = 0;
//       RequestParam 으로 쿼리문 정렬
//       all 이면 전체에서 찾음
        if (genre.equals("all")) {
            List<Webtoon> webtoonList = genreRepository.findAll();
            generWebtoons = webtoonService.getWebtoonDayDto(webtoonList);
        } else {
//           장르가 있으면 장르에서 찾음
            Object[] genreResult = getWebtoonsByGenre(genre);
            generWebtoons = (List<WebtoonDayDto>) genreResult[0];
            num = (Integer) genreResult[1];
        }
        model.addAttribute("num", num);
        model.addAttribute("allWebtoon", generWebtoons);

        return "main/allwebtoon";
    }

    //장르 찾는 메서드
    private Object[] getWebtoonsByGenre(String genre) {
//        페이지에 뿌려줄 버튼 색깔 num은 순서
        Integer num = 0;
//        장르 따라서 찾는 쿼리문 변경
        switch (genre) {
            case "action":
                genre = "액션";
                num = 1;
                break;
            case "fantasy":
                genre = "판타지";
                num = 2;
                break;
            case "romance":
                genre = "로맨스";
                num = 3;
                break;
            case "daily":
                genre = "일상";
                num = 4;
                break;
            case "thriller":
                genre = "스릴러";
                num = 5;
                break;
            case "comedy":
                genre = "개그";
                num = 6;
                break;
            case "martial":
                genre = "무협/사극";
                num = 7;
                break;
            case "drama":
                genre = "드라마";
                num = 8;
                break;
            case "sentimental":
                genre = "감성";
                num = 9;
                break;
            case "sports":
                genre = "스포츠";
                num = 10;
                break;
        }

        List<Webtoon> webtoonList = genreRepository.findAllByGenre(genre);
        List<WebtoonDayDto> webtoonDayDtos = webtoonService.getWebtoonDayDto(webtoonList);

        return new Object[]{webtoonDayDtos, num};
    }

    @GetMapping(value = "/main/myPage")
    public String myPage(Model model, @ModelAttribute("message") String message) {
//        로그인 한 유저 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (message != null) {
            model.addAttribute("message", message);
        }

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "마이 페이지는 로그인 회원만 이용가능합니다.");
            return "/member/memberLoginForm";
        }
//      유저 정보 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);
        MemberInfoFormDto memberInfo = memberService.getUserInfo(userId.getId());
//        구매한 작품들 가져오기
        List<WebtoonDetailDto> userWebtoonList = webtoonService.getUserWebtoon(userId.getId());

        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("userWebtoonList", userWebtoonList);

        return "main/myPage";
    }


    @GetMapping(value = "/main/purchaseHistory")
    public String myPurchaseHistory(Model model) {
//        로그인 한 유저확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("errorMessage", "구매내역은 로그인 회원만 확인가능합니다.");
            return "/member/memberLoginForm";
        }
//          유저 정보 전송
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);

//        구매 목록 뽑아오기
        List<PurchaseHistory> purchaseHistoryList = purchaseHistoryRepository.findByMember(userId);
//      구매 목록에 해당하는 웹툰 리스트
        List<WebtoonEpisodes> episodeList = new ArrayList<>();
        for (PurchaseHistory purchaseHistory : purchaseHistoryList) {
            WebtoonEpisodes episode = purchaseHistory.getWebtoonEpisodes();
            episodeList.add(episode);
        }
//      웹툰의 에피소드 가져오기
        List<EpisodeImgs> episodeImgsList = new ArrayList<>();
//        결론적으로 구매한 에피소드 가져오기
        for (WebtoonEpisodes episode : episodeList) {
            List<EpisodeImgs> episodeImgs = episode.getEpisodeImgsList();
            episodeImgsList.addAll(episodeImgs);
        }
//        구매한 에피소드 dto담기
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();

        for (EpisodeImgs episodeInfoList : episodeImgsList) {
            PurchaseDto purchaseDto = new PurchaseDto();
            WebtoonEpisodes thisEpisode = episodeInfoList.getWebtoonEpisode();

            purchaseDto.setEpisodeId(thisEpisode.getId());
            purchaseDto.setWebtoonId(thisEpisode.getWebtoon().getId());
            purchaseDto.setWebtoonTitle(thisEpisode.getWebtoon().getTitle());

            purchaseDto.setEpisodeTitle(thisEpisode.getEpisodeTitle());
            purchaseDto.setEpisodeLike(thisEpisode.getEpisodeLike());
            purchaseDto.setEpisodeView(thisEpisode.getEpisodeView_count());
            purchaseDto.setThumbnail(episodeInfoList.getThumbnail());
            purchaseDto.setWebtoonPath(episodeInfoList.getWebtoonPath());
            purchaseDto.setPrice(thisEpisode.getEpisodePoint());
            purchaseDto.setStar(thisEpisode.getEpisodeStars());
            purchaseDtoList.add(purchaseDto);
        }
//    유저 정보와 함께 전달
        MemberInfoFormDto memberInfo = memberService.getUserInfo(userId.getId());

        model.addAttribute("memberInfo", memberInfo);
        model.addAttribute("userWebtoonList", purchaseDtoList);

        return "main/purchaseHistory";
    }


    @GetMapping(value = "/main/searchPage")
    public String searchPage() {
        return "main/search";
    }


}
