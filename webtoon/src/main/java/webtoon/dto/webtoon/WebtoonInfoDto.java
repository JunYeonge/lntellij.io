package webtoon.dto.webtoon;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WebtoonInfoDto {
    private Long id;
    private String userNickName;//닉네임
    private String title; // 제목
    private String genre; //장르
    private String webtoonInfo; // 내용
    private String thumbnail1; //썸네일
    private String webtoonPath;//이미지 경로
//    준영
}
