package webtoon.dto.webtoon;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import webtoon.entity.member.Member;
import webtoon.entity.webtoon.Webtoon;

import javax.persistence.*;

@Getter
@Setter
public class WebtoonDto {
    private Long id;

    private Member member;

    private String user_nickName;

    private String registrationDate; // 등록일

    private String title;   // 웹툰명(작품명)

    private String genre;   //장르

    private String day; // 요일

    private String webtoonInfo;

    private double stars; // 별점

    private String thumbnail1;   //썸네일

    private String thumbnail2;   //썸네일

    private String webtoonPath;

    private int view_count;   // 조회수

    private int age_limit;   // 연령제한

    private int allLike;


}
