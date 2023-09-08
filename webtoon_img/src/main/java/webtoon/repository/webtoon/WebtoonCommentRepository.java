package webtoon.repository.webtoon;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webtoon.entity.webtoon.WebtoonComment;

@Repository
public interface WebtoonCommentRepository extends JpaRepository<WebtoonComment,Long> {
}
