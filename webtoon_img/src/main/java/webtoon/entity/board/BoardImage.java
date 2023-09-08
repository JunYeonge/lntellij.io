package webtoon.entity.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "Board_Img")
public class BoardImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageName; // 이미지 파일 이름

    private String imageUrl; // 이미지 파일 URL

    // 다대일(N:1) 관계 설정: BoardImage 엔티티에서 Board 엔티티로의 연관 관계 설정
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
}
