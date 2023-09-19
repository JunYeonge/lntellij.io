package webtoon.dto.episode;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import webtoon.entity.episodes.WebtoonEpisodes;

import javax.persistence.*;

@Getter
@Setter
public class EpisodeImgDto {

        private Long id;

        private MultipartFile webtoonImgPath;

        private String webtoonPath;

        private MultipartFile Thumbnail;

        private Long episodeId;


}
