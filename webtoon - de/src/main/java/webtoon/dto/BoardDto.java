package webtoon.dto;
import lombok.*;
import org.modelmapper.ModelMapper;
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
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardDto of(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

}