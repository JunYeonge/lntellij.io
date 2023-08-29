package toyproject.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyproject.board.dto.BoardCommentDto;
import toyproject.board.repository.BoardCommentRepository;

@Service
@RequiredArgsConstructor
public class BoardCommentService {

    private final BoardCommentRepository boardCommentRepository;



    @Transactional
    public Long saveBoardComment(BoardCommentDto dto){
        boardCommentRepository.save(dto.toEntity());
        return dto.getId();
    }

}
