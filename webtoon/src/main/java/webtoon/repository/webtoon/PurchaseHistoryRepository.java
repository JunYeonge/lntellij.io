package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    PurchaseHistory findOneByMemberAndWebtoonEpisodes(Member member, WebtoonEpisodes webtoonEpisodes);

}
