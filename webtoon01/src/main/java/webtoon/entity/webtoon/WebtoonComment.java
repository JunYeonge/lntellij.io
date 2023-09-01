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
public class WebtoonComment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String user_id;

    @Column
    private String episodes; // 회차

    @Column
    private String likes;    //좋아요

    @Column
    private String no_like; //싫어요

    @Column
    private String registrationDate; // 등록일
}
