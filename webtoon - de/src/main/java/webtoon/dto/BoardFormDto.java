package webtoon.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
public class BoardFormDto {
    private  Long id;

    @NotBlank(message = "제목은 필수로 입력해야합니다.")
    private String title;

    @NotBlank(message = "내용은 필수로 입력해야합니다.")
    private String content;
}

//업데이트는 path매핑