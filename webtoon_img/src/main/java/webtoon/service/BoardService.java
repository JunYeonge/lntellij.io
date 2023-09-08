package webtoon.service;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import webtoon.dto.BoardDto;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardImage;
import webtoon.repository.board.BoardRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Value("${upload.directory}") // 외부 프로퍼티로부터 이미지 업로드 디렉토리 경로를 주입
    private String uploadDirectory;
    @Transactional
    public void save(BoardDto boardDto, List<MultipartFile> images) {
        // 게시물 정보 저장
        Board board = Board.toSaveEntity(boardDto);
        boardRepository.save(board);

        // 이미지 업로드 및 이미지 URL 설정
        List<BoardImage> imageList = new ArrayList<>();
        List<String> imageUrls = saveImages(images);

        for (String imageUrl : imageUrls) {
            BoardImage image = new BoardImage();
            image.setImageUrl(imageUrl);
            image.setBoard(board);
            imageList.add(image);
        }

        // 이미지 URL을 BoardDto에 설정
        boardDto.setImageUrls(imageUrls);

        board.setImages(imageList);
        boardRepository.save(board);
    }

    private void saveImage(MultipartFile imageFile, String imageName) {
        try {
            byte[] bytes = imageFile.getBytes();


            Files.write(path, bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Page<BoardDto> findAll(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(BoardDto::toBoardDto);
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDto findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            BoardDto boardDto = BoardDto.toBoardDto(board);
            return boardDto;
        } else {
            return null;
        }
    }

    public BoardDto update(BoardDto boardDto) {
        Board board = Board.toUpdateEntity(boardDto);
        boardRepository.save(board);
        return findById(board.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public List<String> saveImages(List<MultipartFile> images) {
        List<String> imageUrls = new ArrayList<>();

        for (MultipartFile imageFile : images) {
            if (!imageFile.isEmpty()) {
                try {
                    String fileExtension = StringUtils.getFilenameExtension(imageFile.getOriginalFilename());
                    String imageName = "webtoon_imgfile-" + UUID.randomUUID().toString() + "." + fileExtension;

                    Path imagePath = Path.of(IMAGE_UPLOAD_DIR + imageName);

                    Files.copy(imageFile.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

                    String imageUrl = "/images/" + imageName; // 이미지 URL 생성
                    imageUrls.add(imageUrl);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return imageUrls;
    }
}
