package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webtoon.entity.webtoon.Webtoon;
import webtoon.entity.webtoon.WebtoonData;

import java.util.List;

public interface WebtoonDayRepository extends JpaRepository<Webtoon, Long> {
    //    조회수 정렬 (데일리 웹툰 페이지 기본 값)
    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE %?1% " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithDayByView(String day);

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE %?1% " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithDayByLatestEpisodeDate(String day);

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE %?1% " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithDayByLike(String day);

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE %?1% " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithDayByStar(String day);

}
