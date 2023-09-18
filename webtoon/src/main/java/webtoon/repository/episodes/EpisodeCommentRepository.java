package webtoon.repository.episodes;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.episodes.EpisodeComment;
import webtoon.entity.episodes.EpisodeImgs;

import java.util.List;

public interface EpisodeCommentRepository extends JpaRepository<EpisodeComment, Long> {
    List<EpisodeComment> findByWebtoonEpisodeId(Long episodeId);

}
