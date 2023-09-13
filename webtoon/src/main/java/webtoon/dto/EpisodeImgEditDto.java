package webtoon.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EpisodeImgEditDto {

        private Long id;

        private MultipartFile webtoonImgPath;


        private MultipartFile Thumbnail;

        private Long episodeId;


        private String thumbnailLink;

        private String webtoonLink;

        private String webtoonImgLink;



}
