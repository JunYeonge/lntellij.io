package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webtoon.entity.webtoon.Webtoon;

import java.util.List;

public interface WebtoonRepository extends JpaRepository<Webtoon, Long> {

    @Query("SELECT w FROM Webtoon w " +
            "JOIN w.webtoonData wd " +
            "WHERE w.title LIKE %:searchWord% " +
            "ORDER BY wd.view_count DESC")
    List<Webtoon> findByTitle(@Param("searchWord") String searchWord);

    @Query("SELECT w FROM Webtoon w " +
            "JOIN w.webtoonData wd " +
            "WHERE w.user_nickName LIKE %:searchWord% " +
            "ORDER BY wd.view_count DESC")
    List<Webtoon> findByUser_id(@Param("searchWord") String searchWord);

    @Query("SELECT w FROM Webtoon w " +
            "JOIN w.webtoonData wd " +
            "WHERE w.genre LIKE %:searchWord% " +
            "ORDER BY wd.view_count DESC")
    List<Webtoon> findByGenre(@Param("searchWord") String searchWord);

    @Query("SELECT w FROM Webtoon w " +
            "JOIN w.webtoonData wd " +
            "WHERE w.title LIKE %:searchWord% " +
            "OR w.user_nickName LIKE %:searchWord% " +
            "OR w.genre LIKE %:searchWord% " +
            "ORDER BY wd.view_count DESC")
    List<Webtoon> findByAll(@Param("searchWord") String searchWord);



    List<Webtoon> findByMemberId(Long memberId);



//    준영

    @Query("SELECT w FROM Webtoon w ORDER BY RAND()")
    List<Webtoon> findRandomWebtoons();

    @Query("SELECT w FROM Webtoon w " +
            "ORDER BY w.registrationDate DESC")
    List<Webtoon> findAllByOrderWebtoonData_RegistrationDateDesc();


}
