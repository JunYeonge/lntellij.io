package webtoon.entity.board;


import lombok.*;
import webtoon.dto.BoardDto;


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
    @Column(length = 2000, nullable = false)
    private String content;  // 내용
    @Column(length = 50, nullable = false)
    private String title;  // 제목
    @Column
    private int view_count;   // 조회수

<<<<<<< HEAD
=======

>>>>>>> eb7cef68413b67b4cb0583ba02ed8515b89f93f9
}
