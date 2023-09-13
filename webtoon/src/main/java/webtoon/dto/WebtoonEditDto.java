package webtoon.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class WebtoonEditDto {
    private Long id;

    private String title;   // 웹툰명(작품명)

    @NotBlank(message = "하나 이상의 장르를 선택해주세요.")
    private String genre;   //장르

    @NotBlank(message = "연재 요일을 선택하지 않았습니다.")
    private String day; // 요일

    @NotBlank(message = "작품 설명을 입력하세요.")
    private String webtoonInfo;

    private int age_limit;   // 연령제한

    private MultipartFile thumbnail1;
    private MultipartFile thumbnail2;

    private String webtoonPath;
    private String thum1Link;
    private String thum2Link;

}
