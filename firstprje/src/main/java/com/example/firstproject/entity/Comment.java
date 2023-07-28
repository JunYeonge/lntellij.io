package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;





    public static Comment createComment(CommentDto dto, Article article) {
        // 예외발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패, 댓글의 id가 없어야 합니다.");
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패, 댓글의 id가 없어야합니다.");
            //엔티티 생성 및 반환
            return  new Comment(
                    dto.getId(),
                    article,
                    dto.getNickname(),
                    dto.getBody()
            );
        }


    public void patch(CommentDto dto) {
        //예외발생
        if(this.id != dto.getId()) //this 는 target 를 의미
            throw  new IllegalArgumentException("댓글 수정 실패, 잘못된 id가 입력되었습니다.");

        // 객체을 갱신
        if (dto.getNickname() != null) //this 는 target 의 닉네임
            this.nickname = dto.getNickname();

        if (dto.getBody() != null) // this 는 target 의 body
            this.body = dto.getBody();


    }
}