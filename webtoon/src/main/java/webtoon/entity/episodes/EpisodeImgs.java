package webtoon.entity.episodes;

import lombok.Getter;
import lombok.Setter;
import webtoon.constant.Role;
import webtoon.constant.Thumbnail;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class EpisodeImgs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EpisodeImgs_id")
    private Long id;

    @Column
    private String webtoonImgPath;

    @Column
    private String webtoonPath;

    @Column
    private String Thumbnail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "webtoon_episode_id")
    private WebtoonEpisodes webtoonEpisode;
}
