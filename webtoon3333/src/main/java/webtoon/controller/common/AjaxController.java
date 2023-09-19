package webtoon.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webtoon.dto.webtoon.WebtoonDayDto;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.webtoon.WebtoonDayRepository;
import webtoon.repository.webtoon.WebtoonGenreRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.webtoon.WebtoonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor

public class AjaxController {
    private final WebtoonService webtoonService;
    private final WebtoonDayRepository dayRepository;
    private final WebtoonRepository webtoonRepository;
    private final WebtoonGenreRepository genreRepository;

    // 메인 페이지 제이슨
    @GetMapping("/main/{type}")
    @ResponseBody
    public Map<String, Object> mainWebtoonList(@PathVariable("type") String type) {
        Map<String, Object> responseData = new HashMap<>();
//        type은 조회수 업데이트 좋아요 별점, 이에따라 가져오는 쿼리문이 달라짐
        Map<String, List<Webtoon>> webtoonListByDay = getWebtoonListByCriteria(type);
//        요일 정렬
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("월요일"));
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("화요일"));
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("수요일"));
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("목요일"));
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("금요일"));
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("토요일"));
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("일요일"));

        responseData.put("mondayWebtoons", mondayWebtoonDto);
        responseData.put("tuesdayWebtoons", tuesdayWebtoonDto);
        responseData.put("wednesdayWebtoons", wednesdayWebtoonDto);
        responseData.put("thursdayWebtoons", thursdayWebtoonDto);
        responseData.put("fridayWebtoons", fridayWebtoonDto);
        responseData.put("saturdayWebtoons", saturdayWebtoonDto);
        responseData.put("sundayWebtoons", sundayWebtoonDto);

        return responseData;
    }

    //    메인페이지 정렬
    private Map<String, List<Webtoon>> getWebtoonListByCriteria(String criteria) {
//        카테고리에 따라 가져오는 쿼리문이 다름.
        Map<String, List<Webtoon>> webtoonMap = new HashMap<>();
//        요일 전체 출력
        List<String> daysOfWeek = List.of("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일");

        for (String day : daysOfWeek) {
            List<Webtoon> dayWebtoonList = new ArrayList<>();

            switch (criteria) {
                case "view":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByView(day);
                    break;
                case "Update":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByLatestEpisodeDate(day);
                    break;
                case "like":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByLike(day);
                    break;
                case "star":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByStar(day);
                    break;
            }

            dayWebtoonList = dayWebtoonList.stream()
                    .limit(5)
                    .collect(Collectors.toList());

            webtoonMap.put(day, dayWebtoonList);
        }

        return webtoonMap;
    }


    //    요일웹툰 페이지 제이슨
    @GetMapping("/dailyWebtoon/{type}")
    @ResponseBody
    public Map<String, Object> dailyWebtoonList(@PathVariable("type") String type) {
        Map<String, Object> responseData = new HashMap<>();
//        type은 조회수 업데이트 좋아요 별점, 이에따라 가져오는 쿼리문이 달라짐
        Map<String, List<Webtoon>> webtoonListByDay = getWebtoonListByCriteriaAll(type);
//        요일 정렬
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("월요일"));
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("화요일"));
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("수요일"));
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("목요일"));
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("금요일"));
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("토요일"));
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(webtoonListByDay.get("일요일"));

        responseData.put("mondayWebtoons", mondayWebtoonDto);
        responseData.put("tuesdayWebtoons", tuesdayWebtoonDto);
        responseData.put("wednesdayWebtoons", wednesdayWebtoonDto);
        responseData.put("thursdayWebtoons", thursdayWebtoonDto);
        responseData.put("fridayWebtoons", fridayWebtoonDto);
        responseData.put("saturdayWebtoons", saturdayWebtoonDto);
        responseData.put("sundayWebtoons", sundayWebtoonDto);

        return responseData;
    }

    //  메인페이지  장르 정렬제이슨
    @GetMapping("/genre/{genreName}")
    @ResponseBody
    public Map<String, Object> getGenreWebtoon(@PathVariable String genreName) {
        Map<String, Object> responseData = new HashMap<>();

        if (genreName.equals("romance")) {
            genreName = "로맨스";
        } else if (genreName.equals("fantasy")) {
            genreName = "판타지";
        } else if (genreName.equals("action")) {
            genreName = "액션";
        } else if (genreName.equals("daily")) {
            genreName = "일상";
        } else if (genreName.equals("thriller")) {
            genreName = "스릴러";
        } else if (genreName.equals("comedy")) {
            genreName = "개그";
        } else if (genreName.equals("martial")) {
            genreName = "무협/사극";
        } else if (genreName.equals("drama")) {
            genreName = "드라마";
        } else if (genreName.equals("sentimental")) {
            genreName = "감성";
        } else if (genreName.equals("sports")) {
            genreName = "스포츠";
        }

        List<Webtoon> webtoonList = genreRepository.findAllByGenre(genreName);

        if (webtoonList != null) {
            webtoonList = webtoonList.stream().limit(5).collect(Collectors.toList());
            List<WebtoonDayDto> webtoonDto = webtoonService.getWebtoonDayDto(webtoonList);
            responseData.put("genreWebtoonDto", webtoonDto);
        }

        return responseData;
    }


    //    요일 페이지 제이슨
    private Map<String, List<Webtoon>> getWebtoonListByCriteriaAll(String criteria) {
//        카테고리에 따라 가져오는 쿼리문이 다름.
        Map<String, List<Webtoon>> webtoonMap = new HashMap<>();
//        요일 전체 출력
        List<String> daysOfWeek = List.of("월요일", "화요일", "수요일", "목요일", "금요일", "토요일", "일요일");

        for (String day : daysOfWeek) {
            List<Webtoon> dayWebtoonList = new ArrayList<>();

            switch (criteria) {
                case "view":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByView(day);
                    break;
                case "Update":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByLatestEpisodeDate(day);
                    break;
                case "like":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByLike(day);
                    break;
                case "star":
                    dayWebtoonList = dayRepository.findWebtoonsWithDayByStar(day);
                    break;
            }

            webtoonMap.put(day, dayWebtoonList);
        }

        return webtoonMap;
    }




    @GetMapping("/gender/{type}")
    @ResponseBody
    public Map<String, Object> genderWebtoon(@PathVariable String type, Model model) {
        Map<String, Object> responseData = new HashMap<>();

        List<Webtoon> genderWebtoonList = genreRepository.findByGender(type)
                .stream()
                .limit(5)
                .collect(Collectors.toList());

        responseData.put("genderWebtoonDto", webtoonService.getWebtoonDayDto(genderWebtoonList));
        return responseData;
    }


}
