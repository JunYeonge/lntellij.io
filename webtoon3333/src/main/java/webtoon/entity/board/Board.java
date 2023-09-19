package webtoon.entity.board;


import lombok.*;
import webtoon.dto.board.BoardDto;
import webtoon.entity.BaseEntity;
import webtoon.entity.member.Member;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
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

    @NotBlank(message = "내용을 입력해주세요.")
    @Column
    private String boardContent;  // 내용

    @NotBlank(message = "제목을 입력해주세요.")
//    @Size(max = 30, message = "제목은 30자 이내로 입력해주세요.")
    @Column(length = 30, nullable = false)
    private String boardTitle;  // 제목

    @Column
    private int boardHits;   // 조회수

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<BoardComment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

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