package webtoon.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.transform.ToListResultTransformer;
import org.modelmapper.ModelMapper;
import webtoon.entity.board.Board;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Setter
@Getter
public class BoardFormDto {
    private  Long id;

    @NotBlank(message = "제목은 필수로 입력해야합니다.")
    private String title;

    @NotBlank(message = "내용은 필수로 입력해야합니다.")
    private String content;

    private static ModelMapper modelMapper = new ModelMapper();
    public Board createBoard() {
        return modelMapper.map(this,Board.class);
    }
}
