package webtoon.dto.webtoon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class WebtoonDayDto {

    private Long id;

    private String user_nickName;

    private String title;   // 웹툰명(작품명)

    private String genre;   //장르

    private String day; // 요일

    private String webtoonInfo;

    private String thumbnail1;   //썸네일

    private String thumbnail2;   //썸네일

    private String webtoonPath;

    private int age_limit;   // 연령제한



}
