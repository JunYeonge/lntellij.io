package webtoon.dto.webtoon;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WebtoonViewCountDto {
    private Long id;
    private String title; // 제목
    private String thumbnail1; // 썸네일 이미지
    private String webtoonPath; //이미지 경로
}
