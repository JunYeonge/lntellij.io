package webtoon.dto.episode;

import lombok.Getter;
import lombok.Setter;
import webtoon.entity.episodes.EpisodeComment;

@Getter
@Setter
public class EpisodeCommentDto {
    private Long id;

    private Long episodeId;

    private String user_name;
    private String registrationDate;
    private String comment;

    public static EpisodeCommentDto fromEntity(EpisodeComment comment) {
        EpisodeCommentDto dto = new EpisodeCommentDto();
        dto.setId(comment.getId());
        dto.setEpisodeId(comment.getWebtoonEpisode().getId());
        dto.setRegistrationDate(comment.getRegistrationDate());
        dto.setComment(comment.getComment());
        return dto;
    }
}
