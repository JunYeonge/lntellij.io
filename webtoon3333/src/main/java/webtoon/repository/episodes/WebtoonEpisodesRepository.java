package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.webtoon.WebtoonData;

import java.util.List;

public interface WebtoonEpisodesRepository extends JpaRepository<WebtoonEpisodes,Long> {
    List<WebtoonEpisodes> findByWebtoonId(Long webtoonId);

    @Query("SELECT SUM(e.episodeView_count) FROM WebtoonEpisodes e WHERE e.webtoon.id = :webtoonId")
    int sumEpisodeViewCountByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("SELECT SUM(e.episodeMan_count) FROM WebtoonEpisodes e WHERE e.webtoon.id = :webtoonId")
    int sumEpisodeManCountByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("SELECT SUM(e.episodeGirl_count) FROM WebtoonEpisodes e WHERE e.webtoon.id = :webtoonId")
    int sumEpisodeGirlCountByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("SELECT SUM(e.episodeStars) FROM WebtoonEpisodes e WHERE e.webtoon.id = :webtoonId")
    double sumEpisodeStarsByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("SELECT COUNT(e) FROM WebtoonEpisodes e WHERE e.webtoon.id = :webtoonId")
    long countEpisodeStarsByWebtoonId(@Param("webtoonId") Long webtoonId);

    @Query("SELECT SUM(e.episodeLike) FROM WebtoonEpisodes e WHERE e.webtoon.id = :webtoonId")
    int sumEpisodeLikeByWebtoonId(@Param("webtoonId") Long webtoonId);
}
