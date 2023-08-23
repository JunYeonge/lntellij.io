package webtoon.dto;

import jdk.jshell.Snippet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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
    private LocalDateTime updateTime;
    private LocalDateTime regTime;//등록일

}
