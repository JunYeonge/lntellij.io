package webtoon.dto;


import lombok.*;
import webtoon.entity.board.Board;


import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardDto {
    private Long id;
    private String user_id;
    private String nickname; // 닉네임
    private String content; // 내용
    private String title; // 제목
    private int view_count; //조회수
    private LocalDateTime regTime;//등록일

    public static BoardDto boardDto (Board board){
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setUser_id(board.getUser_id());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        boardDto.setNickname(board.getNickname());
        boardDto.setView_count(board.getView_count());
        boardDto.setRegTime(board.getRegTime());
        return boardDto;
    }

}
