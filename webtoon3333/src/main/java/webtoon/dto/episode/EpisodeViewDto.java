package webtoon.dto.episode;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EpisodeViewDto {
    private Long id;

    private String webtoonPath;

    private String webtoonImgPath;

    private String Thumbnail;

}
