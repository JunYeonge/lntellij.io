package com.codestates.notice_project.Member.entity;

import com.codestates.notice_project.board.entity.Board;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class MemberBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberBoardId;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    public void addMember(Member member) {
        this.member = member;
        if (!this.member.getMemberBoards().contains(this)) {
            this.member.getMemberBoards().add(this);
        }
    }

    public void addBoard(Board board) {
        this.board = board;
        if (!this.board.getMemberBoards().contains(this)) {
            this.board.getMemberBoards().add(this);
        }
    }
}
