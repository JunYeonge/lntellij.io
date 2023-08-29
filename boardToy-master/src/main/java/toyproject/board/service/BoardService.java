package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.domain.Board;
import toyproject.board.dto.BoardCommentDto;
import toyproject.board.dto.BoardDto;
import toyproject.board.repository.BoardRepository;
import toyproject.board.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {

    private final BoardRepository boardRepository;

    private ThreadLocal<Long> countVisitStore = new ThreadLocal<>();

    @Transactional
    public Long saveBoard(BoardDto boardDto) {
        boardRepository.save(boardDto.toEntity());
        return boardDto.getId();
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    @Transactional
    public Page<Board> getBoardList(Pageable pageable) {

        return boardRepository.findAll(pageable);

    }

    public Page<Board> paging(int page) {
        return boardRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id")));

    }

    @Transactional
    public void updateVisit(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow((() ->
                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));

        board.updateVisit(boardDto.getCountVisit());
        log.info("조회 카운트={}",boardDto.getCountVisit());
    }

    public Board findById(Long id){
        Board board = boardRepository.findById(id).get();
        return board;
    }

//    @Transactional
//    public Long BeforeCountVisitLogic(Long id) {
//
//        Board board = boardRepository.findById(id).orElseThrow((() ->
//                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));
//
//        board.updateVisit(countVisitStore.get());
//        sleep(100);
//
//    }


    @Transactional
    public Long countVisitLogic(Long id) {

        Board board = boardRepository.findById(id).orElseThrow((() ->
                new IllegalStateException("해당 게시글이 존재하지 않습니다.")));

        log.info("저장 : ID={} board.getCountVisit={} ",id, countVisitStore.get());
        countVisitStore.set(board.getCountVisit() + 1L);
        board.updateVisit(countVisitStore.get());
        sleep(100);
        log.info("조회 : countVisitStore={}",countVisitStore.get());
        log.info("카운트 횟수={}", board.getCountVisit());

        countVisitStore.remove();
        return countVisitStore.get();
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
