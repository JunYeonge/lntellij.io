package webtoon.entity.board;

import lombok.*;


import javax.persistence.*;

@Entity
@Getter
@Setter
public class Board {   // 게시판

    @Id
    @GeneratedValue
    @Column(name="board_id")
    private Long id;

    @Column
    private String user_id;

    @Column
    private String nickname;    //닉네임

    @Column
    private String registrationDate; // 등록일

    @Column
    private String content;  // 내용

    @Column
    private String title;  // 제목

    @Column
    private int view_count;   // 조회수

}
