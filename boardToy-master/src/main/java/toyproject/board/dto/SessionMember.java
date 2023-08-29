package toyproject.board.dto;

import lombok.Getter;
import toyproject.board.domain.Member;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

    private String username;
    private String email;

    private String picture;

    public SessionMember(Member member) {
        this.username = member.getUsername();
        this.email = member.getEmail();
    }
}
