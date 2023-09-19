package webtoon.entity.board;

import lombok.Getter;
import lombok.Setter;
import webtoon.dto.board.CommentDto;
import webtoon.entity.BaseEntity;
import webtoon.entity.member.Member;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    public static BoardComment toSaveEntity(CommentDto commentDto, Board board) {
        BoardComment commentEntity = new BoardComment();
        commentEntity.setCommentContent(commentDto.getCommentContent());
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setBoard(board);
        return commentEntity;
    }
}