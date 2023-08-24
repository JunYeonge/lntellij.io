package webtoon.entity.board;


import lombok.*;


import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime regTime; // 등록일

    @Column
    private String content;  // 내용

    @Column
    private String title;  // 제목

    @Column
    private int view_count;   // 조회수

    @Builder
    public Board(Long id, String nickname, String content, String title) {
        this.id = id;
        this.nickname = nickname;
        this.content = content;
        this.title = title;
    }
}
