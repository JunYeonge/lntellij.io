package webtoon.entity.webtoon;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Webtoon {  //작품
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "webtoon_id")
    private Long id;

    @Column
    private String user_nickName;

    @Column
    private String registrationDate; // 등록일

    @Column(length = 20)
    private String title;   // 웹툰명(작품명)

    @Column
    private String genre;   //장르

    @Column
    private String day; // 요일

    @Column
    private String webtoonInfo;

    @Column
    private String thumbnail1;   //썸네일

    @Column
    private String thumbnail2;   //썸네일

    @Column
    private String webtoonPath;

    @Column
    private int age_limit;   // 연령제한

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToMany(mappedBy = "webtoon", cascade = CascadeType.ALL)
    private List<WebtoonEpisodes> episodes = new ArrayList<>();

    @OneToOne(mappedBy = "webtoon", cascade = CascadeType.ALL)
    private WebtoonData webtoonData;

}
