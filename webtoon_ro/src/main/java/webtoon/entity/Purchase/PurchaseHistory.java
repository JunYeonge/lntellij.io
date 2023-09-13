package webtoon.entity.Purchase;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class PurchaseHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "episode_id")
    private WebtoonEpisodes webtoonEpisodes;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column
    private String purchaseDay;

    @Column
    private int purchasePrice;
}
