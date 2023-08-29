package spring_study.board_crud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spring_study.board_crud.domain.Board;
import spring_study.board_crud.repository.BoardRepository;


import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public List<Board> findBoards(){
        return boardRepository.findAll();
    }

    public Board findOne(Long id){
        return boardRepository.findById(id).orElseThrow(NullPointerException::new);
    }

    @Transactional
    public void create(Board board) {
        boardRepository.save(board);
    }

    @Transactional
    // Dirty Checking 으로 update 수행
    public void update(Long id, String title, String content) {
        Board board = boardRepository.findById(id).orElseThrow(NullPointerException::new);
        board.setTitle(title);
        board.setContent(content);
    }
    // id에 해당 하는 board 가 repository 에 존재 하지 않을 경우 NullPointerException 에러 핸들링
    // (** 서버 죽지 않게 하기 위함 **)

    public void delete(Board board){
        boardRepository.delete(board);
    }

}
