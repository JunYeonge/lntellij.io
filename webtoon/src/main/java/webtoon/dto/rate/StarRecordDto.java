package webtoon.dto.rate;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;

@Getter
@Setter
public class StarRecordDto {
    private Long id;

    private Long episodeId;

    private Long userId;

    private double userStar;

    private Member member;

    private WebtoonEpisodes episodes;


}
