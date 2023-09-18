package webtoon.dto.purchase;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PurchaseDto {


        private Long episodeId;

        private Long webtoonId;

        private int episodeLike;

        private int episodeView;

        private double star;

        private int price;

        private String webtoonPath;

        private String thumbnail;

        private String episodeTitle;

        private String webtoonTitle;



}
