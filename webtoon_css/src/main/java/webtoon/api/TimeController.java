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

        // Convert datetimeString to LocalDateTime
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetimeString);
        LocalDateTime localDateTime = zonedDateTime.toLocalDateTime();

        // Format LocalDateTime to yyyyMMddHHmmss
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        String formattedDateTime = localDateTime.format(formatter);

        return formattedDateTime;
    }
    public String getServerDay() {
        String apiUrl = "http://worldtimeapi.org/api/timezone/Asia/Seoul";
        RestTemplate restTemplate = new RestTemplate();

        WorldTimeApiResponse response = restTemplate.getForObject(apiUrl, WorldTimeApiResponse.class);

        String datetimeString = response.getDatetime();

        ZonedDateTime zonedDateTime = ZonedDateTime.parse(datetimeString);
        DayOfWeek dayOfWeek = zonedDateTime.getDayOfWeek();

        String dayOfWeekString = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        return dayOfWeekString;
    }
}
