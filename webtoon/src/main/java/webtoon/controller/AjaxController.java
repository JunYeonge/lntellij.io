package webtoon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import webtoon.dto.WebtoonDayDto;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.webtoon.WebtoonDayRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.WebtoonService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor

public class AjaxController {
    private final WebtoonService webtoonService;
    private final WebtoonDayRepository dayRepository;
    private final WebtoonRepository webtoonRepository;

    @GetMapping("/dailyWebtoon/view")
    @ResponseBody
    public Map<String, Object> dayWebtoonListByView() {
        Map<String, Object> responseData = new HashMap<>();

        List<Webtoon> mondayWebtoonList = dayRepository.findWebtoonsWithMondayByView();
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(mondayWebtoonList);

        List<Webtoon> tuesdayWebtoonList = dayRepository.findWebtoonsWithTuesdayByView();
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(tuesdayWebtoonList);

        List<Webtoon> wednesdayWebtoonList = dayRepository.findWebtoonsWithWednesdayByView();
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(wednesdayWebtoonList);

        List<Webtoon> thursdayWebtoonList = dayRepository.findWebtoonsWithThursdayByView();
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(thursdayWebtoonList);

        List<Webtoon> fridayWebtoonList = dayRepository.findWebtoonsWithFridayByView();
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(fridayWebtoonList);

        List<Webtoon> saturdayWebtoonList = dayRepository.findWebtoonsWithSaturdayByView();
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(saturdayWebtoonList);

        List<Webtoon> sundayWebtoonList = dayRepository.findWebtoonsWithSundayByView();
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(sundayWebtoonList);

        responseData.put("mondayWebtoons", mondayWebtoonDto);
        responseData.put("tuesdayWebtoons", tuesdayWebtoonDto);
        responseData.put("wednesdayWebtoons", wednesdayWebtoonDto);
        responseData.put("thursdayWebtoons", thursdayWebtoonDto);
        responseData.put("fridayWebtoons", fridayWebtoonDto);
        responseData.put("saturdayWebtoons", saturdayWebtoonDto);
        responseData.put("sundayWebtoons", sundayWebtoonDto);


        return responseData;
    }

    @GetMapping("/dailyWebtoon/Update")
    @ResponseBody
    public Map<String, Object> dayWebtoonListByUpdate() {
        Map<String, Object> responseData = new HashMap<>();

        List<Webtoon> mondayWebtoonList = dayRepository.findWebtoonsWithMondayByLatestEpisodeDate();
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(mondayWebtoonList);

        List<Webtoon> tuesdayWebtoonList = dayRepository.findWebtoonsWithTuesdayByLatestEpisodeDate();
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(tuesdayWebtoonList);

        List<Webtoon> wednesdayWebtoonList = dayRepository.findWebtoonsWithWednesdayByLatestEpisodeDate();
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(wednesdayWebtoonList);

        List<Webtoon> thursdayWebtoonList = dayRepository.findWebtoonsWithThursdayByLatestEpisodeDate();
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(thursdayWebtoonList);

        List<Webtoon> fridayWebtoonList = dayRepository.findWebtoonsWithFridayByLatestEpisodeDate();
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(fridayWebtoonList);

        List<Webtoon> saturdayWebtoonList = dayRepository.findWebtoonsWithSaturdayByLatestEpisodeDate();
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(saturdayWebtoonList);

        List<Webtoon> sundayWebtoonList = dayRepository.findWebtoonsWithSundayByLatestEpisodeDate();
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(sundayWebtoonList);

        responseData.put("mondayWebtoons", mondayWebtoonDto);
        responseData.put("tuesdayWebtoons", tuesdayWebtoonDto);
        responseData.put("wednesdayWebtoons", wednesdayWebtoonDto);
        responseData.put("thursdayWebtoons", thursdayWebtoonDto);
        responseData.put("fridayWebtoons", fridayWebtoonDto);
        responseData.put("saturdayWebtoons", saturdayWebtoonDto);
        responseData.put("sundayWebtoons", sundayWebtoonDto);

        return responseData;
    }




    @GetMapping("/dailyWebtoon/like")
    @ResponseBody
    public Map<String, Object> dayWebtoonListByLike() {
        Map<String, Object> responseData = new HashMap<>();

        List<Webtoon> mondayWebtoonList = dayRepository.findWebtoonsWithMondayByLike();
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(mondayWebtoonList);

        List<Webtoon> tuesdayWebtoonList = dayRepository.findWebtoonsWithTuesdayByLike();
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(tuesdayWebtoonList);

        List<Webtoon> wednesdayWebtoonList = dayRepository.findWebtoonsWithWednesdayByLike();
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(wednesdayWebtoonList);

        List<Webtoon> thursdayWebtoonList = dayRepository.findWebtoonsWithThursdayByLike();
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(thursdayWebtoonList);

        List<Webtoon> fridayWebtoonList = dayRepository.findWebtoonsWithFridayByLike();
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(fridayWebtoonList);

        List<Webtoon> saturdayWebtoonList = dayRepository.findWebtoonsWithSaturdayByLike();
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(saturdayWebtoonList);

        List<Webtoon> sundayWebtoonList = dayRepository.findWebtoonsWithSundayByLike();
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(sundayWebtoonList);

        responseData.put("mondayWebtoons", mondayWebtoonDto);
        responseData.put("tuesdayWebtoons", tuesdayWebtoonDto);
        responseData.put("wednesdayWebtoons", wednesdayWebtoonDto);
        responseData.put("thursdayWebtoons", thursdayWebtoonDto);
        responseData.put("fridayWebtoons", fridayWebtoonDto);
        responseData.put("saturdayWebtoons", saturdayWebtoonDto);
        responseData.put("sundayWebtoons", sundayWebtoonDto);

        return responseData;
    }

    @GetMapping("/dailyWebtoon/star")
    @ResponseBody
    public Map<String, Object> dayWebtoonListByStar() {
        Map<String, Object> responseData = new HashMap<>();

        List<Webtoon> mondayWebtoonList = dayRepository.findWebtoonsWithMondayByStar();
        List<WebtoonDayDto> mondayWebtoonDto = webtoonService.getWebtoonDayDto(mondayWebtoonList);

        List<Webtoon> tuesdayWebtoonList = dayRepository.findWebtoonsWithTuesdayByStar();
        List<WebtoonDayDto> tuesdayWebtoonDto = webtoonService.getWebtoonDayDto(tuesdayWebtoonList);

        List<Webtoon> wednesdayWebtoonList = dayRepository.findWebtoonsWithWednesdayByStar();
        List<WebtoonDayDto> wednesdayWebtoonDto = webtoonService.getWebtoonDayDto(wednesdayWebtoonList);

        List<Webtoon> thursdayWebtoonList = dayRepository.findWebtoonsWithThursdayByStar();
        List<WebtoonDayDto> thursdayWebtoonDto = webtoonService.getWebtoonDayDto(thursdayWebtoonList);

        List<Webtoon> fridayWebtoonList = dayRepository.findWebtoonsWithFridayByStar();
        List<WebtoonDayDto> fridayWebtoonDto = webtoonService.getWebtoonDayDto(fridayWebtoonList);

        List<Webtoon> saturdayWebtoonList = dayRepository.findWebtoonsWithSaturdayByStar();
        List<WebtoonDayDto> saturdayWebtoonDto = webtoonService.getWebtoonDayDto(saturdayWebtoonList);

        List<Webtoon> sundayWebtoonList = dayRepository.findWebtoonsWithSundayByStar();
        List<WebtoonDayDto> sundayWebtoonDto = webtoonService.getWebtoonDayDto(sundayWebtoonList);

        responseData.put("mondayWebtoons", mondayWebtoonDto);
        responseData.put("tuesdayWebtoons", tuesdayWebtoonDto);
        responseData.put("wednesdayWebtoons", wednesdayWebtoonDto);
        responseData.put("thursdayWebtoons", thursdayWebtoonDto);
        responseData.put("fridayWebtoons", fridayWebtoonDto);
        responseData.put("saturdayWebtoons", saturdayWebtoonDto);
        responseData.put("sundayWebtoons", sundayWebtoonDto);

        return responseData;
    }
}
