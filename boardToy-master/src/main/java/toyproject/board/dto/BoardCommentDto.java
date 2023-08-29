package toyproject.board.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import toyproject.board.domain.Board;
import toyproject.board.domain.BoardComment;
import toyproject.board.domain.Member;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BoardCommentDto {

    private Long id;
    private String content;
    private LocalDateTime created_date;
    private String created_by;
    private Character delete_check;
    private Board board;
    private Member member;

    @Builder
    public BoardCommentDto(String content, LocalDateTime created_date, String created_by, Character delete_check, Board board, Member member) {
        this.content = content;
        this.created_date = created_date;
        this.created_by = created_by;
        this.delete_check = delete_check;
        if(this.board != null){
            board.getBoardCommentList().remove(this);
        }else
            this.board = board;
        if(this.member != null){
            member.getBoardCommentList().remove(this);
        }else
            this.member = member;
    }

    public BoardComment toEntity() {
        return BoardComment.builder()
                .content(content)
                .created_date(created_date)
                .created_by(created_by)
                .delete_check(delete_check)
                .member(member)
                .board(board)
                .build();

    }
}
