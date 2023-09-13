package webtoon.entity.webtoon;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.member.Member;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class WebtoonData {  //작품 정보
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "webtoon_data_id")
    private Long id;

    @Column
    private double stars; // 별점

    @Column
    private int view_count;   // 조회수

    @Column
    private int man_count;  //남자 카운트

    @Column
    private int girl_count;

    @Column
    private int allLike;

    @Column
    private int ratedBy;

    @OneToOne
    @JoinColumn(name = "webtoon_id")
    private Webtoon webtoon;

}
