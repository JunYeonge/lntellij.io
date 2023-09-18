package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import webtoon.entity.Purchase.PurchaseHistory;
import webtoon.entity.episodes.WebtoonEpisodes;
import webtoon.entity.member.Member;

import java.util.List;

public interface PurchaseHistoryRepository extends JpaRepository<PurchaseHistory, Long> {
    PurchaseHistory findOneByMemberAndWebtoonEpisodes(Member member, WebtoonEpisodes webtoonEpisodes);

    List<PurchaseHistory> findByMember(Member member);

}
