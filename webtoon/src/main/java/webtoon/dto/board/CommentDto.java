package webtoon.dto.board;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.board.BoardComment;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContent;
    private LocalDateTime regTime;

    public static CommentDto toCommentDTO(BoardComment BoardComment, Long id) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(BoardComment.getId());
        commentDto.setCommentWriter(BoardComment.getCommentWriter());
        commentDto.setCommentContent(BoardComment.getCommentContent());
        commentDto.setRegTime(BoardComment.getRegTime());
        return commentDto;
    }
}