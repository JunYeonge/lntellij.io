package webtoon.entity.board;


import lombok.*;
import webtoon.api.TimeController;
import webtoon.dto.BoardDto;
import webtoon.entity.BaseEntity;


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


    public static Board toSaveEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setTitle(boardDto.getTitle());
        board.setNickname(boardDto.getNickname());
        board.setContent(boardDto.getContent());
        board.setView_count(0);

        return board;
    }
}
