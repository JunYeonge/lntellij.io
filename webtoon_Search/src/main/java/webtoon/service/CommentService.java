package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import webtoon.dto.CommentDto;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;
import webtoon.repository.board.BoardRepository;
import webtoon.repository.board.CommentRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;


    public Long save(CommentDto commentDto) {
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
        Board boardEntity = boardRepository.findById(boardId).get();
        List<BoardComment> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
        /* EntityList -> DTOList */
        List<CommentDto> commentDTOList = new ArrayList<>();
        for (BoardComment commentEntity: commentEntityList) {
            CommentDto commentDTO = CommentDto.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}


