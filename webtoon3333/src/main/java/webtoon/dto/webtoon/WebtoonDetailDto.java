package webtoon.dto.webtoon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class WebtoonDetailDto {
    private Long id;

    private String user_nickName;

    private String registrationDate;

    private String title;

    private double star;

    private int view;

    private int like;

    private String genre;

    private String day;

    private String webtoonInfo;

    private String thumbnail1;

    private String thumbnail2;

    private String webtoonPath;

    private int age_limit;   // 연령제한
}
