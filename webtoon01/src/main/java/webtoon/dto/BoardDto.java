package webtoon.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private String title;
    private String content;
    private String username;



//    public static BoardDto boardDto (Board board){
//        BoardDto boardDto = new BoardDto();
//        boardDto.setId(board.getId());
//        boardDto.setUser_id(board.getUser_id());
//        boardDto.setTitle(board.getTitle());
//        boardDto.setContent(board.getContent());
//        boardDto.setNickname(board.getNickname());
//        boardDto.setView_count(board.getView_count());
//        boardDto.setRegTime(LocalDateTime.now());
//        return boardDto;
//    }
}