package webtoon.dto.episode;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class EpisodeFormDto {
    private Long id;

    private Long webtoonId;

    private int price;

//    @NotBlank(message = "회차명을 입력하세요.")
    private String title;


}
