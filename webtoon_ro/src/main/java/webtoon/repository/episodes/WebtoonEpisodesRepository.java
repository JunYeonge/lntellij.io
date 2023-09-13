package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.webtoon.WebtoonData;

import java.util.List;

public interface WebtoonEpisodesRepository extends JpaRepository<WebtoonEpisodes,Long> {
    List<WebtoonEpisodes> findByWebtoonId(Long webtoonId);

}
