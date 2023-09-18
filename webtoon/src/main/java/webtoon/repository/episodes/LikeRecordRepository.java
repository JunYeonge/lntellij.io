package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webtoon.entity.episodes.LikeRecord;
import webtoon.entity.episodes.StarRecord;

public interface LikeRecordRepository extends JpaRepository<LikeRecord,Long> {

    LikeRecord findByUserId(Long userId);

    LikeRecord findByUserIdAndEpisodeId(Long userId, Long episodeId);

    @Query("SELECT COUNT(e) FROM LikeRecord e WHERE e.episodeId = :episodeId")
    int countEpisodeLikeByEpisodeId(@Param("episodeId") Long episodeId);


}
