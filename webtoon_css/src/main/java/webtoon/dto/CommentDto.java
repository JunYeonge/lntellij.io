package webtoon.dto;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.board.BoardComment;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String commentWriter;
    private  String commentContent;
    private Long boardId;
    private LocalDateTime regTime;

    public static CommentDto toCommentDTO(BoardComment BoardComment, Long boardId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(BoardComment.getId());
        commentDto.setCommentWriter(BoardComment.getCommentWriter());
        commentDto.setCommentContent(BoardComment.getCommentContent());
        commentDto.setRegTime(BoardComment.getRegTime());
        commentDto.setBoardId(boardId);
        return commentDto;
    }
}
