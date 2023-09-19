package webtoon.controller.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webtoon.api.TimeController;
import webtoon.config.CustomUserDetails;
import webtoon.dto.episode.EpisodeCommentDto;
import webtoon.dto.episode.EpisodeEditDto;
import webtoon.dto.episode.EpisodeImgEditDto;
import webtoon.entity.episodes.EpisodeComment;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;
import webtoon.repository.episodes.EpisodeCommentRepository;
import webtoon.repository.episodes.WebtoonEpisodesRepository;
import webtoon.repository.member.MemberRepository;

import javax.validation.Valid;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class EpisodeCommentController {
    private final MemberRepository memberRepository;
    private final WebtoonEpisodesRepository episodesRepository;
    private final TimeController timeController;
    private final EpisodeCommentRepository episodeCommentRepository;

    @PostMapping(value = "/createEpisodeComment")
    public String EditWebtoon(@Valid EpisodeCommentDto episodeCommentForm) {

        WebtoonEpisodes thisEpisode = episodesRepository.findById(episodeCommentForm.getEpisodeId()).orElse(null);


        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String info = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(info);
//
            EpisodeComment episodeComment = new EpisodeComment();

            episodeComment.setId(episodeComment.getId());
            episodeComment.setMember(userId);
            episodeComment.setWebtoonEpisode(thisEpisode);
            episodeComment.setRegistrationDate(timeController.getServerTime());
            episodeComment.setNickname(userId.getNickname());
            episodeComment.setComment(episodeCommentForm.getComment());

            episodeCommentRepository.save(episodeComment);
            return "redirect:/webtoonPage/" + thisEpisode.getWebtoon().getId() + "/episode/" + thisEpisode.getId() + "?message=" + URLEncoder.encode("댓글이 등록되었습니다.", StandardCharsets.UTF_8);
        } catch (
                Exception e) {
            return "redirect:/webtoonPage/" + thisEpisode.getWebtoon().getId() + "/episode/" + thisEpisode.getId() + "?message=" + URLEncoder.encode("댓글이 등록되었습니다.", StandardCharsets.UTF_8);

        }

    }
}
