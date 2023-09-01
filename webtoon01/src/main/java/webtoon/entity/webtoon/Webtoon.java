package webtoon.entity.webtoon;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Webtoon {  //작품
    @Id
    @GeneratedValue
    @Column(name = "webtoon_id")
    private Long id;

    @Column
    private String user_id;

    @Column
    private String registrationDate; // 등록일

    @Column
    private String title;   // 웹툰명(작품명)

    @Column
    private String genre;   //장르

    @Column
    private String day; // 요일

    @Column
    private double stars; // 별점

    @Column
    private String thumbnail1;   //썸네일

    @Column
    private String thumbnail2;   //썸네일

    @Column
    private int view_count;   // 조회수

    @Column
    private int age_limit;   // 연령제한


}