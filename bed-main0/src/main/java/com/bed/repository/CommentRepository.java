package com.bed.repository;

import com.bed.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "select * from comment where as_form_id = :asFormId",nativeQuery = true)
    List<Comment> findByAsFormId(@Param("asFormId") Long articleId);
    @Query(value = "select * from comment where nickname = :nickname",nativeQuery = true)
    List<Comment> findByNickname(@Param("nickname") String nickname);
}
