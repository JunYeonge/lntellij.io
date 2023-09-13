package webtoon.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webtoon.entity.board.BoardComment;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private Long id;
    private String content;
    private String user_id; // 댓글 작성자
    private LocalDateTime createdAt;

    public static CommentDto fromEntity(BoardComment commentEntity) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentEntity.getId());
        commentDto.setContent(commentEntity.getContent());
        commentDto.setUser_id(commentEntity.getMember().getUser_id()); // 댓글 작성자 정보 가져오기
        commentDto.setCreatedAt(commentEntity.getCreatedAt());
        return commentDto;
    }
    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
    }
}
