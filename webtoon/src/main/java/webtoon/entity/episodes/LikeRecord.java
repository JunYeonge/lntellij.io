package webtoon.entity.episodes;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class LikeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long episodeId;
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_episode_id")
    private WebtoonEpisodes episodes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

}