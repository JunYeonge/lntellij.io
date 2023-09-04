package com.codestates.notice_project.board.dto;

import com.codestates.notice_project.board.entity.Board;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class BoardDto {

    @Getter
    public static class Post{

        @NotBlank
        private String writer;

        @NotBlank
        private String comment;
    }

    @Getter @Setter
    public static class Patch{
        private long boardId;
        private String writer;
        private String comment;
        private Board.BoardStatus boardStatus;
    }

    @Getter
    @Builder
    public static class Response{
        private long boardId;
        private String writer;
        private String comment;
        private Board.BoardStatus boardStatus;

        private String getBoardStatus(){return boardStatus.getStatus();}
    }
}
