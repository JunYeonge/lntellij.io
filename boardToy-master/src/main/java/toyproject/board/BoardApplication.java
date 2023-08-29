package toyproject.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import toyproject.board.domain.Member;
import toyproject.board.dto.MemberDto;

//(exclude = SecurityAutoConfiguration.class )
@SpringBootApplication
@EnableJpaAuditing
public class BoardApplication {


    public static void main(String[] args) {




        SpringApplication.run(BoardApplication.class, args);
    }

}
