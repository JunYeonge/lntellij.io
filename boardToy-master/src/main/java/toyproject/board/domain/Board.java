package toyproject.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String content;
    private String createdBy;
    private Long countVisit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") // 외래키를 설정
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<BoardComment> boardCommentList = new ArrayList<>();

    public void setMember(Member member) {
        this.member = member;
        member.getBoardList().add(this);
    }

    @Builder
    public Board(String title, String content, String createdBy, Long countVisit, Member member, List<BoardComment> boardCommentList) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.countVisit = countVisit;
        if (this.member != null) {
            member.getBoardList().remove(this);
        }
        this.boardCommentList = boardCommentList;

    }




    public void updateVisit(Long countVisit) {
        this.countVisit = countVisit;
    }

    // pr testing
}
