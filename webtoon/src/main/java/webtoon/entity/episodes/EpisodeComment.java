package webtoon.entity.episodes;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class EpisodeComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EpisodeComment_id")
    private Long id;

    @Column
    private String nickname;

    @Column
    private String registrationDate; // 등록일

    @Column
    private String comment;

    @ManyToOne
    @JoinColumn(name = "webtoon_episode_id")
    private WebtoonEpisodes webtoonEpisode;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
