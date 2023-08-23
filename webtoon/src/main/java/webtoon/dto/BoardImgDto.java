package webtoon.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import webtoon.entity.board.BoardImg;

@Getter
@Setter
public class BoardImgDto {
    private Long id;

    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardImgDto of(BoardImg boardImg){
        return modelMapper.map(boardImg, BoardImgDto.class);
    }

}
