package webtoon.entity.episodes;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class WebtoonEpisodes {  // 회차
    @Id
    @GeneratedValue
    @Column(name="session_id")
    private Long id;

    @Column
    private String user_id;

    @Column
    private String title; // 회차 작품명

    @Column
    private double stars; //별점

    @Column
    private long view_count;   // 조회수

    @Column
    private String registrationDate; // 등록일

    @Column
    private String imgUrl ; //이미지

    @Column
    private int point; //포인트

    @Column
    private int man_count;  //남자 카운트
    @Column
    private int girl_count;  //여자 카운트
}
