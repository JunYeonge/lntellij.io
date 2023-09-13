package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.webtoon.Webtoon;
import webtoon.entity.webtoon.WebtoonData;

import java.util.List;

public interface WebtoonDataRepository extends JpaRepository<WebtoonData, Long> {

    List<WebtoonData> findByWebtoonId(Long webtoonId);

}
