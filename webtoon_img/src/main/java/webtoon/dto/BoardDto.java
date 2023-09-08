package webtoon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import webtoon.entity.board.Board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private Long id;
    private String boardWriter; // 작가
    private String boardPass;
    private String boardContent; // 내용
    private String boardTitle; // 제목
    private int boardHits; // 조회수
    private LocalDateTime boardRegTime; // 올린 시간
    private LocalDateTime boardUpdateTime; // 수정 시간
    private String imageName;
    private List<String> imageUrls; // 이미지 URL 리스트

    // 이미지 업로드를 위한 필드 추가
    private List<MultipartFile> images = new ArrayList<>();

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

    // 게터와 세터는 필요한 경우 추가하세요.

    // 이미지 업로드에 필요한 메서드를 추가하세요.
    public void addImage(MultipartFile image) {
        this.images.add(image);
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
