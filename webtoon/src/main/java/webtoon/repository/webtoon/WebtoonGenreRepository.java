package webtoon.repository.webtoon;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import webtoon.entity.webtoon.Webtoon;

import java.util.List;

public interface WebtoonGenreRepository extends JpaRepository<Webtoon, Long> {
    @Query("SELECT w FROM Webtoon w " +
            "WHERE w.genre LIKE %:genre% " +
            "ORDER BY w.webtoonData.view_count DESC")
    List<Webtoon> findAllByGenre(@Param("genre") String genre);


    @Query("SELECT w FROM Webtoon w " +
            "ORDER BY CASE WHEN :sortType = 'girl_count' THEN w.webtoonData.girl_count END DESC, " +
            "CASE WHEN :sortType = 'man_count' THEN w.webtoonData.man_count END DESC, " +
            "CASE WHEN :sortType = 'view_count' THEN w.webtoonData.view_count END DESC")
    List<Webtoon> findByGender(@Param("sortType") String sortType);

}
