package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.episodes.EpisodeImgs;
import webtoon.entity.webtoon.WebtoonData;

import java.util.List;

public interface EpisodeImgRepository extends JpaRepository<EpisodeImgs, Long> {
    List<EpisodeImgs> findByWebtoonEpisodeId(Long webtoonEpisodeId);

}
