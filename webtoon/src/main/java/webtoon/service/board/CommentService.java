package webtoon.service.board;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import webtoon.config.CustomUserDetails;
import webtoon.dto.board.CommentDto;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;
import webtoon.entity.member.Member;
import webtoon.repository.board.BoardRepository;
import webtoon.repository.board.CommentRepository;
import webtoon.repository.member.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public Long save(CommentDto commentDto) {
        /* 부모엔티티(BoardEntity) 조회 */
        Optional<Board> optionalBoardEntity = boardRepository.findById(commentDto.getId());
        if (optionalBoardEntity.isPresent()) {
            Board boardEntity = optionalBoardEntity.get();
            BoardComment commentEntity = BoardComment.toSaveEntity(commentDto, boardEntity);

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String info = userDetails.getEmail();
            Member userId = memberRepository.findByEmail(info);

            commentEntity.setMember(userId);

            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }

    public List<CommentDto> findAll(Long id) {
        Board board = boardRepository.findById(id).get();
        List<BoardComment> BoardCommentList = commentRepository.findAllByBoardOrderByIdDesc(board);

        /* EntityList -> DTOList */
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (BoardComment boardComment: BoardCommentList) {
            CommentDto commentDto = CommentDto.toCommentDTO(boardComment, id);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
