//package toyproject.board.domain.service;
//
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.assertj.core.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@RunWith(SpringRunner.class)
//@Transactional
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class ControllerTest {
//
//    @Autowired
//    private TestRestTemplate testRestTemplate;
//
//    @Test
//    void welcome_page_test (){
//    //given
//        String body = this.testRestTemplate.getForObject("/", String.class);
//        //when
//        assertThat(body).contains("Home");
//    //then
//    }
//
//
//
//}