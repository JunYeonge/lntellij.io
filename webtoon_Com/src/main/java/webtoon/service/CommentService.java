package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import webtoon.dto.CommentDto;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;
import webtoon.repository.board.BoardRepository;
import webtoon.repository.board.CommentRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<Board> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            Board boardEntity = optionalBoardEntity.get();
            BoardComment commentEntity = BoardComment.toSaveEntity(commentDto, boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDto> findAll(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        List<BoardComment> BoardCommentList = commentRepository.findAllByBoardOrderByIdDesc(board);
        /* EntityList -> DTOList */
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (BoardComment boardComment: BoardCommentList) {
            CommentDto commentDto = CommentDto.toCommentDTO(boardComment, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}

