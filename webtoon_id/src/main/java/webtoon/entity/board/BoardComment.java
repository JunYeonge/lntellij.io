package webtoon.entity.board;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class BoardComment {
    @Id
    @GeneratedValue
    private Long id;


}