package webtoon.entity.board;


import lombok.*;
import webtoon.dto.BoardDto;
import webtoon.dto.BoardFormDto;
import webtoon.entity.BaseEntity;
import webtoon.entity.member.Member;


import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
public class Board extends BaseEntity {   // 게시판

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
    private Long id;

    @Column
    private String user_id;

    @Column
    private String nickname;    //닉네임

    @Column(length = 2000, nullable = false)
    private String content;  // 내용

    @Column(length = 50, nullable = false)
    private String title;  // 제목

    @Column
    private int view_count;   // 조회수

    @Column
    private LocalDateTime createDate = LocalDateTime.now();// 생성일

    @Column
    private LocalDateTime modifiedDate; // 수정일

    public void updateboard(BoardFormDto boardFormDto) {
        this.title = boardFormDto.getTitle();
        this.content = boardFormDto.getContent();
        this.view_count = boardFormDto.getView_count();
        this.nickname = boardFormDto.getNickname();
    }

}
