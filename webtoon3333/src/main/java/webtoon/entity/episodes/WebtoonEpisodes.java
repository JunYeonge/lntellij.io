package webtoon.entity.episodes;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.webtoon.Webtoon;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class WebtoonEpisodes {  // 회차
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "episodes_id")
    private Long id;

    @Column
    private String episodeTitle; // 회차 작품명

    @Column
    private String episodeRegistrationDate; // 등록일

    @Column
    private int episodePoint;

    @Column
    private double episodeStars; //별점

    @Column
    private int episodeLike;

    @Column
    private int episodeView_count;   // 조회수

    @Column
    private int episodeMan_count;  //남자 카운트

    @Column
    private int episodeGirl_count;  //여자 카운트

    @ManyToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

    @OneToMany(mappedBy = "webtoonEpisode", cascade = CascadeType.ALL)
    private List<EpisodeImgs> episodeImgsList = new ArrayList<>();

    @OneToMany(mappedBy = "webtoonEpisode", cascade = CascadeType.ALL)
    private List<EpisodeComment> episodeComments = new ArrayList<>();

    @OneToMany(mappedBy = "webtoonEpisodes", cascade = CascadeType.ALL)
    private List<PurchaseHistory> purchaseHistories = new ArrayList<>();
}
