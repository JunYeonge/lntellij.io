package webtoon.controller.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webtoon.config.CustomUserDetails;
import webtoon.entity.episodes.LikeRecord;
import webtoon.entity.episodes.StarRecord;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.WebtoonData;
import webtoon.repository.episodes.LikeRecordRepository;
import webtoon.repository.episodes.StarRecordRepository;
import webtoon.repository.episodes.WebtoonEpisodesRepository;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.WebtoonDataRepository;
import webtoon.repository.webtoon.WebtoonRepository;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor

public class EpisodeRateController {
    private final MemberRepository memberRepository;
    private final StarRecordRepository starRecordRepository;
    private final WebtoonEpisodesRepository episodesRepository;
    private final WebtoonRepository webtoonRepository;
    private final WebtoonDataRepository webtoonDataRepository;
    private final LikeRecordRepository likeRecordRepository;

    @PostMapping(value = "/checkLike")
    public String checkLike(@RequestParam("episodeId") Long episodeId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);

        WebtoonEpisodes episodes = episodesRepository.findById(episodeId).orElse(null);
        Long webtoonId = episodes.getWebtoon().getId();


        LikeRecord checkLike = likeRecordRepository.findByUserIdAndEpisodeId(userId.getId(), episodeId);

        String message = "";

        if (checkLike != null) {
//            이미 준 유저면 별점만 업데이트
            likeRecordRepository.delete(checkLike);
            message = "좋아요가 취소되었어요";
        } else {
//        새 별점은 엔티티 생성
            LikeRecord likeRecord = new LikeRecord();
            likeRecord.setEpisodeId(episodeId);
            likeRecord.setEpisodes(episodes);
            likeRecord.setMember(userId);
            likeRecord.setUserId(userId.getId());

            likeRecordRepository.save(likeRecord);
            message = "좋아요 눌렀어요";
        }


        int totalEpisodeLike = likeRecordRepository.countEpisodeLikeByEpisodeId(episodeId);
        episodes.setEpisodeLike(totalEpisodeLike);
        episodesRepository.save(episodes);

        WebtoonData setLikeData = episodes.getWebtoon().getWebtoonData();
//
        int allEpisodeLike = episodesRepository.sumEpisodeLikeByWebtoonId(webtoonId);
        setLikeData.setAllLike(allEpisodeLike);
        webtoonDataRepository.save(setLikeData);

        return "redirect:/webtoonPage/" + webtoonId + "/episode/" + episodeId + "?message=" + URLEncoder.encode(message, StandardCharsets.UTF_8);

    }


    @PostMapping(value = "/checkStar")
    public String checkStar(@RequestParam("rating") double rating, @RequestParam("episodeId") Long episodeId) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);

        WebtoonEpisodes episodes = episodesRepository.findById(episodeId).orElse(null);
        Long webtoonId = episodes.getWebtoon().getId();

        if (rating == 0) {
            return "redirect:/webtoonPage/" + webtoonId + "/episode/" + episodeId + "?message=" + URLEncoder.encode("0점은 줄 수 없습니다.", StandardCharsets.UTF_8);
        }


        StarRecord checkStars = starRecordRepository.findByUserIdAndEpisodeId(userId.getId(), episodeId);


        if (checkStars != null) {
//            이미 준 유저면 별점만 업데이트
            checkStars.setUserStar(rating);
            starRecordRepository.save(checkStars);
        } else {
//        새 별점은 엔티티 생성
            StarRecord starRecord = new StarRecord();
            starRecord.setMember(userId);
            starRecord.setUserId(userId.getId());
            starRecord.setUserStar(rating);
            starRecord.setEpisodeId(episodeId);
            starRecord.setEpisodes(episodes);
            starRecordRepository.save(starRecord);
        }
//    이제 사람수로 나눠야함
        double totalStar = starRecordRepository.sumEpisodeRateByEpisodeId(episodeId);
        long totalStarJoin = starRecordRepository.countEpisodeRateByEpisodeId(episodeId);

        double result = Math.round((totalStar / totalStarJoin) * 100.0) / 100.0;

        episodes.setEpisodeStars(result);
        episodesRepository.save(episodes);


        double totalWebtoonStar = episodesRepository.sumEpisodeStarsByWebtoonId(webtoonId);
        long totalWebtoonStarJoin = episodesRepository.countEpisodeStarsByWebtoonId(webtoonId);

        double lastResult = Math.round((totalWebtoonStar / totalWebtoonStarJoin) * 100.0) / 100.0;

        WebtoonData setStarData = episodes.getWebtoon().getWebtoonData();
        setStarData.setStars(lastResult);

        webtoonDataRepository.save(setStarData);

        return "redirect:/webtoonPage/" + webtoonId + "/episode/" + episodeId + "?message=" + URLEncoder.encode("별점이 등록되었습니다.", StandardCharsets.UTF_8);
    }


}
