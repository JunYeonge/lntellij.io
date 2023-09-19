package webtoon.service.episode;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.api.TimeController;
import webtoon.dto.episode.EpisodeEditDto;
import webtoon.dto.episode.EpisodeFormDto;
import webtoon.dto.episode.EpisodeImgEditDto;
import webtoon.entity.episodes.EpisodeImgs;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.webtoon.Webtoon;
import webtoon.repository.episodes.EpisodeImgRepository;
import webtoon.repository.episodes.WebtoonEpisodesRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.webtoon.WebtoonService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeService {

    @Value("${newWebtoonFilePath}")
    private String newWebtoonFilePath;

    private final TimeController timeController;
    private final WebtoonService webtoonService;
    private final WebtoonRepository webtoonRepository;
    private final WebtoonEpisodesRepository episodesRepository;
    private final EpisodeImgRepository episodeImgRepository;
    public Long saveEpisode(EpisodeFormDto episodeFormDto) {
        WebtoonEpisodes episode = new WebtoonEpisodes();

        episode.setEpisodeView_count(0);
        episode.setEpisodeStars(0);
        episode.setEpisodeLike(0);
        episode.setEpisodeGirl_count(0);
        episode.setEpisodeMan_count(0);

        episode.setEpisodeTitle(episodeFormDto.getTitle());
        episode.setEpisodePoint(episodeFormDto.getPrice());

        Optional<Webtoon> webtoonOptional = webtoonRepository.findById(episodeFormDto.getWebtoonId());
        webtoonOptional.ifPresent(webtoon -> {
            episode.setWebtoon(webtoon);
        });

        episode.setEpisodeRegistrationDate(timeController.getServerTime());


        episodesRepository.save(episode);
        return episode.getId();
    }

//    public void editEpisode(WebtoonEditDto webtoonEditDto) throws Exception {
    public void editEpisode(EpisodeEditDto episodeEditDto, EpisodeImgEditDto episodeImgEditDto) throws Exception {

        WebtoonEpisodes editEpisode = episodesRepository.findById(episodeEditDto.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 에피스드가 없습니다."));

        EpisodeImgs editEpisodeImg = episodeImgRepository.findById(episodeEditDto.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 회차이미지를 없습니다."));

        editEpisode.setEpisodePoint(episodeEditDto.getPrice());
        editEpisode.setEpisodeTitle(episodeEditDto.getTitle());


//        전에 저장된 이미지 링크
//        String editLink = editWebtoon.getWebtoonPath().substring(editWebtoon.getWebtoonPath().lastIndexOf('/') + 1);
//        String editLink = editEpisodeImg.getWebtoonPath().substring(editEpisodeImg.getWebtoonPath().lastIndexOf('/') + 1);
        String editLink = editEpisodeImg.getWebtoonPath().substring( editEpisodeImg.getWebtoonPath().indexOf('/', 1));
        System.out.println("help");

        System.out.println(editLink);
        String uploadDir = newWebtoonFilePath + "/" + editLink;
        System.out.println(uploadDir);

        // 원래 파일의 이름을 가져와서 저장할 파일명 생성
        String fileName1 = editEpisodeImg.getThumbnail();
        String fileName2 = editEpisodeImg.getWebtoonImgPath();
        System.out.println(fileName1);
        System.out.println(fileName2);
        // 저장할 파일 경로
        Path filePath1 = Paths.get(uploadDir, fileName1);
        Path filePath2 = Paths.get(uploadDir, fileName2);
        System.out.println(filePath1);
        System.out.println(filePath2);
        System.out.println("help");
//        예외처리
        if (episodeImgEditDto.getThumbnail() != null && !episodeImgEditDto.getThumbnail().isEmpty()) {
            try {
                // 이미지 파일 열기
                InputStream inputStream1 = episodeImgEditDto.getThumbnail().getInputStream();

                // 이미지 파일 저장
                Files.copy(inputStream1, filePath1, StandardCopyOption.REPLACE_EXISTING);

                // 이미지 파일 닫기 // 이거 진짜 필수임
                inputStream1.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception("첫 번째 이미지 업로드 중 오류가 발생했습니다.");
            }
        }

// 두 번째 이미지 업로드
        if (episodeImgEditDto.getWebtoonImgPath() != null && !episodeImgEditDto.getWebtoonImgPath().isEmpty()) {
            try {
                // 이미지 파일 열기
                InputStream inputStream2 = episodeImgEditDto.getWebtoonImgPath().getInputStream();

                // 이미지 파일 저장
                Files.copy(inputStream2, filePath2, StandardCopyOption.REPLACE_EXISTING);

                // 이미지 파일 닫기 // 이거 진짜 필수임
                inputStream2.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception("두 번째 이미지 업로드 중 오류가 발생했습니다.");
            }
        }
    }
}
