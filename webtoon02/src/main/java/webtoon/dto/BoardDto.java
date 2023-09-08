package webtoon.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webtoon.entity.board.Board;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String boardWriter;//작가
    private String boardPass;
    private String boardContent; // 내용
    private String boardTitle; // 제목
    private int boardHits; //조회수
    private LocalDateTime boardRegTime; //올린 시간
    private LocalDateTime boardUpdateTime; //수정 시간

    public static BoardDto toBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setBoardWriter(board.getBoardWriter());
        boardDto.setBoardPass(board.getBoardPass());
        boardDto.setBoardTitle(board.getBoardTitle());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardHits(board.getBoardHits());
        boardDto.setBoardUpdateTime(board.getUpdateTime());
        boardDto.setBoardRegTime(board.getRegTime());
        return boardDto;
    }

    public BoardDto(String boardWriter, String boardTitle, int boardHits, LocalDateTime boardRegTime) {
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardRegTime = boardRegTime;
    }

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