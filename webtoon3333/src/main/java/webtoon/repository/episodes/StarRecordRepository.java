package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webtoon.entity.episodes.StarRecord;

public interface StarRecordRepository extends JpaRepository<StarRecord,Long> {

    StarRecord findByUserId(Long userId);

    StarRecord findByUserIdAndEpisodeId(Long userId, Long episodeId);


    @Query("SELECT SUM(e.userStar) FROM StarRecord e WHERE e.episodeId = :episodeId")
    double sumEpisodeRateByEpisodeId(@Param("episodeId") Long episodeId);

    @Query("SELECT COUNT(e) FROM StarRecord e WHERE e.episodeId = :episodeId")
    long countEpisodeRateByEpisodeId(@Param("episodeId") Long episodeId);

}
