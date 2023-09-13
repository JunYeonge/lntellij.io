package webtoon.entity.board;


import lombok.*;
import webtoon.constant.Announcement;
import webtoon.dto.BoardDto;
import webtoon.entity.BaseEntity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "board_table")
public class Board extends BaseEntity {   // 게시판

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String boardWriter;

    @Lob
    private String boardContent;  // 내용

    @Column(length = 50, nullable = false)
    private String boardTitle;  // 제목

    @Column
    private int boardHits;   // 조회수


    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardComment> comments = new ArrayList<>();

    public static Board toSaveEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContent(boardDto.getBoardContent());
        board.setBoardHits(0);
        return board;
    }


    public static Board toUpdateEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setId(boardDto.getId());
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContent(boardDto.getBoardContent());
        board.setBoardHits(boardDto.getBoardHits());
        return board;
    }
}

