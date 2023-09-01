package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webtoon.entity.webtoon.Webtoon;

@Repository
public interface WebtoonRepository extends JpaRepository<Webtoon,Long> {
}
