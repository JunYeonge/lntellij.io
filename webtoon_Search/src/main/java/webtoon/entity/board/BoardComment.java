package webtoon.entity.board;

import lombok.Getter;
import lombok.Setter;
import webtoon.dto.CommentDto;
import webtoon.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments")
public class BoardComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContent;

    /* Board:Comment = 1:N */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;


    public static BoardComment toSaveEntity(CommentDto commentDto, Board board) {
        BoardComment commentEntity = new BoardComment();
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContent(commentDto.getCommentContent());
        commentEntity.setBoard(board);
        return commentEntity;
    }
}