package toyproject.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Board;
import toyproject.board.dto.BoardDto;
import toyproject.board.repository.BoardRepository;
import toyproject.board.service.BoardService;

import static org.junit.Assert.*;


//@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BoardControllerTest {

    @Autowired
    BoardService boardService;

    @Test
    public void BeforeCountLogic () throws Exception{
        // given
        log.info("main start");
        Board board = boardService.findById(1L);
        BoardDto boardDto = BoardDto.builder()
                .countVisit(board.getCountVisit()+1L)
                .build();
        // when
        Runnable test1 = () -> boardService.updateVisit(board.getId(), boardDto);
        Runnable test2 = () -> boardService.updateVisit(board.getId(), boardDto);
        // then
        Thread threadA = new Thread(test1);
        threadA.setName("thread-A");

        Thread threadB = new Thread(test2);
        threadB.setName("thread-B");
        // then
        threadA.start(); // thread-A 비지니스 로직 실행
        sleep(200);
        threadB.start(); // thread-B 비지니스 로직 실행

        sleep(3000); // 메인 쓰레드 종료 대기
        log.info("카운트={}",board.getCountVisit());
     }

    @Test
    @Transactional
    public void ThreadTest () throws Exception{
        // given
        log.info("main start");
        Runnable count1 = () -> boardService.countVisitLogic(1L);
        Runnable count2 = () -> boardService.countVisitLogic(1L);
        // when
        Thread threadA = new Thread(count1);
        threadA.setName("thread-A");

        Thread threadB = new Thread(count2);
        threadB.setName("thread-B");
        // then
        threadA.start(); // thread-A 비지니스 로직 실행
        sleep(200);
        threadB.start(); // thread-B 비지니스 로직 실행

        sleep(3000); // 메인 쓰레드 종료 대기


        Board board1 = boardService.findById(1L);
        Long countV1 = board1.getCountVisit();
        log.info("조회 : 카운트={}",countV1);
        log.info("main exit");
     }
    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



//    @Test
//    void field() {
//        Runnable userA = () -> service.logic("userA");
//        Runnable userB = () -> service.logic("userB");
//
//        Thread threadA = new Thread(userA);
//        threadA.setName("thread-A");
//
//        Thread threadB = new Thread(userB);
//        threadB.setName("thread-B");
//
//        threadA.start(); // thread-A 비지니스 로직 실행
//        sleep(100);
//        threadB.start(); // thread-B 비지니스 로직 실행
//
//        sleep(3000); // 메인 쓰레드 종료 대기
//    }
//    public class ThreadLocalService {
//        private ThreadLocal<String> nameStore = new ThreadLocal<>();
//
//        public String logic(String name) {
//            nameStore.set(name);
//            sleep(1000);
//            return nameStore.get();
//        }
//
//        private void sleep(int millis) {
//            try {
//                Thread.sleep(millis);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}