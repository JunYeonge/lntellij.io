package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webtoon.entity.episodes.WebtoonEpisodes;

@Repository
public interface WebtoonEpisodesRepository extends JpaRepository<WebtoonEpisodes,Long> {
}
