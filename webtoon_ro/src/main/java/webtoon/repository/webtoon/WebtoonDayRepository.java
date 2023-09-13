package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import webtoon.entity.webtoon.Webtoon;
import webtoon.entity.webtoon.WebtoonData;

import java.util.List;

public interface WebtoonDayRepository extends JpaRepository<Webtoon, Long> {
//    조회수 정렬 (데일리 웹툰 페이지 기본 값)
    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%월요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithMondayByView();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%화요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithTuesdayByView();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%수요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithWednesdayByView();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%목요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithThursdayByView();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%금요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithFridayByView();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%토요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithSaturdayByView();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%일요일%' " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findWebtoonsWithSundayByView();


//    좋아요 순
@Query("SELECT w FROM Webtoon w " +
        "WHERE w.day LIKE '%월요일%' " +
        "ORDER BY w.webtoonData.allLike DESC")
List<Webtoon> findWebtoonsWithMondayByLike();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%화요일%' " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithTuesdayByLike();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%수요일%' " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithWednesdayByLike();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%목요일%' " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithThursdayByLike();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%금요일%' " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithFridayByLike();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%토요일%' " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithSaturdayByLike();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%일요일%' " +
            "ORDER BY w.webtoonData.allLike DESC")
    List<Webtoon> findWebtoonsWithSundayByLike();


//  별점순

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%월요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithMondayByStar();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%화요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithTuesdayByStar();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%수요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithWednesdayByStar();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%목요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithThursdayByStar();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%금요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithFridayByStar();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%토요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithSaturdayByStar();

    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.day LIKE '%일요일%' " +
            "ORDER BY w.webtoonData.stars DESC")
    List<Webtoon> findWebtoonsWithSundayByStar();


    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%월요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithMondayByLatestEpisodeDate();

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%화요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithTuesdayByLatestEpisodeDate();

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%수요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithWednesdayByLatestEpisodeDate();

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%목요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithThursdayByLatestEpisodeDate();

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%금요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithFridayByLatestEpisodeDate();

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%토요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithSaturdayByLatestEpisodeDate();

    @Query(value = "SELECT * FROM webtoon w " +
            "WHERE w.day LIKE '%일요일%' " +
            "ORDER BY (SELECT MAX(e.episode_registration_date) FROM webtoon_episodes e WHERE e.webtoon_id = w.webtoon_id) DESC", nativeQuery = true)
    List<Webtoon> findWebtoonsWithSundayByLatestEpisodeDate();



}
