package webtoon.service.episode;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import webtoon.api.TimeController;
import webtoon.dto.episode.EpisodeImgDto;
import webtoon.entity.episodes.EpisodeImgs;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.repository.episodes.EpisodeImgRepository;
import webtoon.repository.episodes.WebtoonEpisodesRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.fileSave.FileService;

import java.io.File;

@Service
@Transactional
@RequiredArgsConstructor
public class EpisodeImgService {
    @Value("${newWebtoonFilePath}")
    private String WebtoonPath;

    private final WebtoonEpisodesRepository episodesRepository;
    private final WebtoonRepository webtoonRepository;
    private final TimeController timeController;
    private final EpisodeImgRepository episodeImgRepository;
    private final FileService fileService;

    public void saveEpisodeImg(EpisodeImgDto episodeImgDto) throws Exception {
        EpisodeImgs episodeImgs = new EpisodeImgs();


        WebtoonEpisodes episode = episodesRepository.findById(episodeImgDto.getEpisodeId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 회차의 이미지를 저장하다 문제가 생겼어요!"));
        episodeImgs.setWebtoonEpisode(episode);

        String webtoonUrl = episode.getWebtoon().getWebtoonPath();
        String[] folderName = webtoonUrl.split("/");
        String webtoonFolderName = folderName[folderName.length - 1];

        String episodeFolderName = episode.getEpisodeTitle() + timeController.getServerTime();

        String newEpisodePath = WebtoonPath + "/" + webtoonFolderName;
        File folder = new File(newEpisodePath, episodeFolderName);
        folder.mkdirs();
        String path = episode.getWebtoon().getWebtoonPath() + "/" + episodeFolderName;

        episodeImgs.setWebtoonPath(path);

        String thumbnail = "";
        String webtoon_img_path = "";
//     path + "/" + 파일명으로 가져올거임

        thumbnail = fileService.uploadFile(WebtoonPath + "/" + path.replaceFirst("/webtoon", "") + "/", episodeImgDto.getThumbnail().getOriginalFilename(), episodeImgDto.getThumbnail().getBytes());
        webtoon_img_path = fileService.uploadFile(WebtoonPath + "/" + path.replaceFirst("/webtoon", "") + "/", episodeImgDto.getWebtoonImgPath().getOriginalFilename(), episodeImgDto.getWebtoonImgPath().getBytes());

        episodeImgs.setThumbnail(thumbnail);
        episodeImgs.setWebtoonImgPath(webtoon_img_path);
        episodeImgRepository.save(episodeImgs);
    }


}
