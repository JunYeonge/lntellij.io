package com.codestates.notice_project.Member.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberDto {

    @Getter
    public static class Post {

        @NotBlank(message = "이름을 입력하세요.")
        private String name;

        @NotBlank
        private String password;

        @NotBlank
        @Email
        private String email;
    }

    @Getter
    public static class Patch {

        @Setter
        private long memberId;
        private String name;
        private String password;
        private String email;
    }

    @AllArgsConstructor
    @Getter
    public static class Response {

        private long memberId;
        private String name;
        private String email;
    }
}
