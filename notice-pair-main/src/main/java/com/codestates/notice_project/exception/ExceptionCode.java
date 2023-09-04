package com.codestates.notice_project.exception;

import lombok.Getter;

public enum ExceptionCode {
    BOARD_NOT_FOUND(404, "Board Not Found"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_EXIST(409, "Member Exist");

    @Getter
    private int status;

    @Getter
    private String message;

    ExceptionCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
