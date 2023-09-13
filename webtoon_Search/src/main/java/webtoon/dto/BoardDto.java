package webtoon.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import webtoon.entity.board.Board;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String boardWriter;//작가
    @NotBlank(message = "내용을 입력하세요")
    private String boardContent; // 내용
    @NotBlank(message = "제목을 입력하세요")
    private String boardTitle; // 제목
    private int boardHits; //조회수
    private LocalDateTime boardRegTime; //올린 시간
    private LocalDateTime boardUpdateTime; //수정 시간

    public static BoardDto toBoardDto(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setBoardWriter(board.getBoardWriter());
        boardDto.setBoardTitle(board.getBoardTitle());
        boardDto.setBoardContent(board.getBoardContent());
        boardDto.setBoardHits(board.getBoardHits());
        boardDto.setBoardUpdateTime(board.getUpdateTime());
        boardDto.setBoardRegTime(board.getRegTime());
        return boardDto;
    }

    // 엔티티에서 DTO로 변환하는 메서드
    public static BoardDto fromEntity(Board board) {
        BoardDto dto = new BoardDto();
        dto.setId(board.getId());
        dto.setBoardTitle(board.getBoardTitle());
        dto.setBoardContent(board.getBoardContent());
        // 다른 필드들 복사
        return dto;
    }
}