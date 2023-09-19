package webtoon.dto.webtoon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebtoonDetailImgDto {
    private Long id;

    private String registrationDate;

    private String title;

    private String thumbnail;

    private String webtoonPath;

    private double star;

    private int price;

    private int view;

    private int like;
}
