package webtoon.service.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.api.TimeController;
import webtoon.dto.webtoon.*;
import webtoon.entity.episodes.EpisodeImgs;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;
import webtoon.entity.webtoon.WebtoonData;
import webtoon.repository.episodes.EpisodeImgRepository;
import webtoon.repository.episodes.WebtoonEpisodesRepository;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.WebtoonDataRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.fileSave.FileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class WebtoonService {
    @Value("${newWebtoonFilePath}")
    private String newWebtoonFilePath;

    private final WebtoonRepository webtoonRepository;
    private final WebtoonDataService webtoonDataService;
    private final FileService fileService;
    private final MemberRepository memberRepository;
    private final WebtoonDataRepository webtoonDataRepository;
    private final WebtoonEpisodesRepository webtoonEpisodesRepository;
    private final EpisodeImgRepository episodeImgRepository;


    public void saveWebtoon(WebtoonFormDto webtoonFormDto) throws Exception {


        Member Member = memberRepository.findById(webtoonFormDto.getId()).get();
        Webtoon webtoon = new Webtoon();
        webtoon.setMember(Member);
        webtoon.setUser_nickName(Member.getNickname());
        webtoon.setDay(webtoonFormDto.getDay());
        webtoon.setWebtoonInfo(webtoonFormDto.getWebtoonInfo());
        webtoon.setGenre(webtoonFormDto.getGenre());
        webtoon.setTitle(webtoonFormDto.getTitle());
        webtoon.setAge_limit(webtoonFormDto.getAge_limit());

//        서버시간 설정 api
        TimeController timeController = new TimeController();
//        서버시간 설정
        webtoon.setRegistrationDate(timeController.getServerTime());


//        작품 등록하는 경로
        String webtoonName = webtoon.getTitle() + timeController.getServerTime();
//                       여기는 WebMvcConfig 경로  + 작품 이름으로 폴더 경로 설정
        webtoon.setWebtoonPath("/webtoon/" + webtoonName);

        // 폴더 경로와 작품등록 시간 결합하여 생성할 폴더 생성
        File folder = new File(newWebtoonFilePath, webtoonName);
        if (folder.exists()) {
            // 이미 같은 이름의 폴더가 존재하면 예외를 던집니다.
            throw new IllegalArgumentException("같은 이름의 폴더가 이미 존재합니다.");
        }

        folder.mkdirs();

        String imgName1 = "";
        String imgName2 = "";
//        웹툰 썸내일 저장 및 경로 저장
        imgName1 = fileService.uploadFile(newWebtoonFilePath + "/" + webtoonName, webtoonFormDto.getThumbnail1().getOriginalFilename(), webtoonFormDto.getThumbnail1().getBytes());
        imgName2 = fileService.uploadFile(newWebtoonFilePath + "/" + webtoonName, webtoonFormDto.getThumbnail2().getOriginalFilename(), webtoonFormDto.getThumbnail2().getBytes());

        webtoon.setThumbnail1(imgName1);
        webtoon.setThumbnail2(imgName2);
//        작품 등록
        webtoonDataService.saveWebtoonData(webtoon);
        webtoonRepository.save(webtoon);

    }


    public List<Webtoon> getLastThreeWebtoons() {
        PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "registrationDate"));
        List<Webtoon> latestWebtoons = webtoonRepository.findAll(pageRequest).getContent();

        // 문자열 형태의 날짜를 LocalDateTime으로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        latestWebtoons.forEach(webtoon -> {
            String dateString = webtoon.getRegistrationDate(); // "2023_08_29_18_45_30"
            LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
            String formattedDate = dateTime.format(formatter); // 다시 문자열로 변환
            webtoon.setRegistrationDate(formattedDate);
        });

        return latestWebtoons;
    }

    public List<WebtoonDayDto> getWebtoonDayDto(List<Webtoon> webtoonList) {
        List<WebtoonDayDto> dtoList = new ArrayList<>();
        for (Webtoon webtoon : webtoonList) {
            WebtoonDayDto dto = new WebtoonDayDto();
            dto.setId(webtoon.getId());
            dto.setUser_nickName(webtoon.getUser_nickName());
            dto.setTitle(webtoon.getTitle());
            dto.setGenre(webtoon.getGenre());
            dto.setDay(webtoon.getDay());
            dto.setWebtoonInfo(webtoon.getWebtoonInfo());
            dto.setThumbnail1(webtoon.getThumbnail1());
            dto.setThumbnail2(webtoon.getThumbnail2());
            dto.setWebtoonPath(webtoon.getWebtoonPath());
            dto.setAge_limit(webtoon.getAge_limit());
            dtoList.add(dto);
        }
        return dtoList;
    }


    public WebtoonDetailDto getWebtoonDetailForm(Webtoon webtoon) {


        WebtoonDetailDto webtoonDetailDto = new WebtoonDetailDto();

        WebtoonData webtoonDataList = webtoonDataRepository.findByWebtoonId(webtoon.getId());
        // 원하는 작업 수행
            webtoonDetailDto.setId(webtoon.getId());
            webtoonDetailDto.setUser_nickName(webtoon.getUser_nickName());
            webtoonDetailDto.setGenre(webtoon.getGenre());
            webtoonDetailDto.setTitle(webtoon.getTitle());
            webtoonDetailDto.setWebtoonInfo(webtoon.getWebtoonInfo());
            webtoonDetailDto.setDay(webtoon.getDay());
            webtoonDetailDto.setAge_limit(webtoon.getAge_limit());
            webtoonDetailDto.setStar(webtoonDataList.getStars());
            webtoonDetailDto.setView(webtoonDataList.getView_count());
            webtoonDetailDto.setLike(webtoonDataList.getAllLike());

            webtoonDetailDto.setWebtoonPath(webtoon.getWebtoonPath());
            webtoonDetailDto.setThumbnail1(webtoon.getThumbnail1());
            webtoonDetailDto.setThumbnail2(webtoon.getThumbnail2());
            webtoonDetailDto.setRegistrationDate(webtoon.getRegistrationDate());


        return webtoonDetailDto;
    }


    public List<WebtoonDetailImgDto> getWebtoonDetailImgForm(Long webtoonId) {

        List<WebtoonEpisodes> episodesList = webtoonEpisodesRepository.findByWebtoonId(webtoonId);

        List<WebtoonDetailImgDto> webtoonImgDataList = new ArrayList<>();

        // episodesList를 반복하면서 각 요소를 DTO로 변환하여 리스트에 추가
        for (WebtoonEpisodes episode : episodesList) {
            WebtoonDetailImgDto webtoonImgData = new WebtoonDetailImgDto();

//            여기서 못 가져옴 여기서 수정
            List<EpisodeImgs> episodeImgList = episodeImgRepository.findByWebtoonEpisodeId(episode.getId());
            if (!episodeImgList.isEmpty()) {
                EpisodeImgs episodeImg = episodeImgList.get(0);

                // episodeImg를 처리하는 코드

                webtoonImgData.setId(episode.getId());
                webtoonImgData.setTitle(episode.getEpisodeTitle());
                webtoonImgData.setStar(episode.getEpisodeStars());
                webtoonImgData.setView(episode.getEpisodeView_count());
                webtoonImgData.setPrice(episode.getEpisodePoint());
                webtoonImgData.setLike(episode.getEpisodeLike());
                webtoonImgData.setRegistrationDate(episode.getEpisodeRegistrationDate());
                webtoonImgData.setWebtoonPath(episodeImg.getWebtoonPath());
                webtoonImgData.setThumbnail(episodeImg.getThumbnail());

                webtoonImgDataList.add(webtoonImgData); // 리스트에 추가
            }
        }

        return webtoonImgDataList;
    }

    public void editWebtoon(WebtoonEditDto webtoonEditDto) throws Exception {
        Webtoon editWebtoon = webtoonRepository.findById(webtoonEditDto.getId())
                .orElseThrow(() -> new NoSuchElementException("해당 웹툰은 없습니다."));

        editWebtoon.setDay(webtoonEditDto.getDay());
        editWebtoon.setWebtoonInfo(webtoonEditDto.getWebtoonInfo());
        editWebtoon.setGenre(webtoonEditDto.getGenre());
        editWebtoon.setTitle(webtoonEditDto.getTitle());
        editWebtoon.setAge_limit(webtoonEditDto.getAge_limit());

//        전에 저장된 이미지 링크
        String editLink = editWebtoon.getWebtoonPath().substring(editWebtoon.getWebtoonPath().lastIndexOf('/') + 1);

        String uploadDir = newWebtoonFilePath + "/" + editLink;

        // 원래 파일의 이름을 가져와서 저장할 파일명 생성
        String fileName1 = editWebtoon.getThumbnail1();
        String fileName2 = editWebtoon.getThumbnail2();

        // 저장할 파일 경로
        Path filePath1 = Paths.get(uploadDir, fileName1);
        Path filePath2 = Paths.get(uploadDir, fileName2);

//        예외처리
        if (webtoonEditDto.getThumbnail1() != null && !webtoonEditDto.getThumbnail1().isEmpty()) {
            try {
                // 이미지 파일 열기
                InputStream inputStream1 = webtoonEditDto.getThumbnail1().getInputStream();

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
        if (webtoonEditDto.getThumbnail2() != null && !webtoonEditDto.getThumbnail2().isEmpty()) {
            try {
                // 이미지 파일 열기
                InputStream inputStream2 = webtoonEditDto.getThumbnail2().getInputStream();

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

    public List<WebtoonDetailDto> getUserWebtoon(Long userId) {
//        유저 pk 가지고 그에 유저가 만든 작품리스트 가져오기
        List<Webtoon> webtoons = webtoonRepository.findByMemberId(userId);
//        애는 그거 담을거 리스트
        List<WebtoonDetailDto> userWebtoonList = new ArrayList<>();
//        가져온 항목을 dto에 넣기
        for (Webtoon webtoon : webtoons) {
            WebtoonDetailDto dto = new WebtoonDetailDto();

            WebtoonData webtoonData = webtoonDataRepository.findByWebtoonId(webtoon.getId());
            // Webtoon 객체의 필드 값을 WebtoonDetailDto에 설정
            dto.setLike(webtoonData.getAllLike());
            dto.setStar(webtoonData.getStars());
            dto.setView(webtoonData.getView_count());

            dto.setId(webtoon.getId());
            dto.setUser_nickName(webtoon.getUser_nickName());
            dto.setRegistrationDate(webtoon.getRegistrationDate());
            dto.setTitle(webtoon.getTitle());
            dto.setGenre(webtoon.getGenre());
            dto.setDay(webtoon.getDay());
            dto.setWebtoonInfo(webtoon.getWebtoonInfo());
            dto.setThumbnail1(webtoon.getThumbnail1());
            dto.setThumbnail2(webtoon.getThumbnail2());
            dto.setWebtoonPath(webtoon.getWebtoonPath());
            dto.setAge_limit(webtoon.getAge_limit());

            userWebtoonList.add(dto);
        }

        return userWebtoonList;
    }


//    수정 보완

    //    준영

    public List<WebtoonViewCountDto> getWebtoonRandomCount() {
        List<Webtoon> webtoons = webtoonRepository.findRandomWebtoons();

        return webtoons.stream()
                .map(webtoon -> {
                    WebtoonViewCountDto dto = new WebtoonViewCountDto();
                    dto.setId(webtoon.getId());
                    dto.setTitle(webtoon.getTitle());
                    dto.setThumbnail1(webtoon.getThumbnail1());
                    dto.setWebtoonPath(webtoon.getWebtoonPath());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public List<WebtoonInfoDto> getWebtoonsByRegistrationDate() {
        List<Webtoon> webtoons = webtoonRepository.findAllByOrderWebtoonData_RegistrationDateDesc();

        List<WebtoonInfoDto> dtos = new ArrayList<>();
        for (Webtoon webtoon : webtoons) {
            WebtoonInfoDto dto = new WebtoonInfoDto();
            dto.setId(webtoon.getId());
            dto.setUserNickName(webtoon.getUser_nickName());
            dto.setTitle(webtoon.getTitle());
            dto.setGenre(webtoon.getGenre());
            dto.setWebtoonInfo(webtoon.getWebtoonInfo());
            dto.setThumbnail1(webtoon.getThumbnail1());
            dto.setWebtoonPath(webtoon.getWebtoonPath());

            dtos.add(dto);
        }

        return dtos;
    }

}
