package webtoon.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

@RestController
public class TimeController {
    public String getServerTime() {
        String apiUrl = "http://worldtimeapi.org/api/timezone/Asia/Seoul";
        RestTemplate restTemplate = new RestTemplate();

        WorldTimeApiResponse response = restTemplate.getForObject(apiUrl, WorldTimeApiResponse.class);

        String datetimeString = response.getDatetime();
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetimeString);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

//        연도/월/일/시간/초 단위로 내 편한대로 재구성함.
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }
    public int getServerDay() {
        String apiUrl = "http://worldtimeapi.org/api/timezone/Asia/Seoul";
        RestTemplate restTemplate = new RestTemplate();

        WorldTimeApiResponse response = restTemplate.getForObject(apiUrl, WorldTimeApiResponse.class);

        String datetimeString = response.getDatetime();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetimeString);
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();
        int dayOfWeekValue = dayOfWeek.getValue();
        return dayOfWeekValue;
    }
}
