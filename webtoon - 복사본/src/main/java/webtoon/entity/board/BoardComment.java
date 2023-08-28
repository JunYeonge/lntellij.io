package webtoon.entity.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class BoardComment {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String nickname;

    @Column
    private String registrationDate; // 등록일

    @Column
    private String content;  // 내용

    @Column
    private int view_count;   // 조회수
}
