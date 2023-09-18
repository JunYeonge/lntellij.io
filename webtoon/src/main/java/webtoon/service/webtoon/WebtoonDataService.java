package webtoon.service.webtoon;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.entity.webtoon.Webtoon;
import webtoon.entity.webtoon.WebtoonData;
import webtoon.repository.member.MemberRepository;
import webtoon.repository.webtoon.WebtoonDataRepository;
import webtoon.repository.webtoon.WebtoonRepository;
import webtoon.service.fileSave.FileService;

@Service
@Transactional
@RequiredArgsConstructor
public class WebtoonDataService {
    private final WebtoonRepository webtoonRepository;
    private final WebtoonDataRepository webtoonDataRepository;
    private final FileService fileService;
    private final MemberRepository memberRepository;

    public Long saveWebtoonData(Webtoon webtoon) {
        WebtoonData webtoonData = new WebtoonData();

        webtoonData.setStars(0);
        webtoonData.setView_count(0);
        webtoonData.setMan_count(0);
        webtoonData.setGirl_count(0);
        webtoonData.setAllLike(0);
        webtoonData.setWebtoon(webtoon);

        webtoonDataRepository.save(webtoonData);
        return webtoonData.getId();
    }
}
