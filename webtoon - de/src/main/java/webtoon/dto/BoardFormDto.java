package webtoon.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import webtoon.entity.board.Board;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class BoardFormDto {
    private  Long id;

    @NotBlank(message = "제목은 필수로 입력해야합니다.")
    private String title;

    @NotBlank(message = "내용은 필수로 입력해야합니다.")
    private String content;

    private int view_count;

    private String nickname;

    private List<BoardDto> boardDtoList = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Board createBoard() {
        return modelMapper.map(this, Board.class);
    }

    public static BoardFormDto of(Board board) {
        return modelMapper.map(board, BoardFormDto.class);
    }
}

//업데이트는 path매핑