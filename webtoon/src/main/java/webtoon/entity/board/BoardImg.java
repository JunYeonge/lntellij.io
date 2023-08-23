package webtoon.entity.board;


import lombok.Getter;
import lombok.Setter;
import webtoon.entity.BaseEntity;

import javax.persistence.*;


@Entity
@Table(name = "board_img")
@Getter
@Setter
public class BoardImg extends BaseEntity {
    @Id
    @Column(name = "board_img_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String imgName;
    private String oriImgName; // 원본 이미지 파일명
    private String imgUrl; // 이미지 조회 경로
    private String repImgYn; // 대표 이미지 여부
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public BoardImg(String imgName, String oriImgName, String imgUrl) {
        this.imgName = imgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
    }
}
