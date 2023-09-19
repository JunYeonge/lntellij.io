package webtoon.dto.episode;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EpisodeEditDto {
    private Long id;

    private Long webtoonId;

    private int price;

//    @NotBlank(message = "회차명을 입력하세요.")
    private String title;


}
