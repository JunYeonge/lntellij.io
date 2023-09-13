package webtoon.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import webtoon.dto.CommentDto;
import webtoon.entity.board.Board;
import webtoon.entity.board.BoardComment;
import webtoon.entity.member.Member;
import webtoon.repository.board.BoardRepository;
import webtoon.repository.board.CommentRepository;
import webtoon.repository.member.MemberRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository; // Member 엔티티를 가져오기 위한 리포지토리 (필요한 경우)

    public List<BoardComment> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }

    public void addComment(Long boardId, CommentDto commentDto) {
        // 게시물 엔티티 가져오기
        Board board = boardRepository.findById(boardId).orElse(null);

        if (board != null) {
            // CommentDto에서 필요한 정보 추출
            String userId = commentDto.getUserId(); // 사용자 ID (user_id)
            String content = commentDto.getContent();

            // Member 엔티티 조회 (사용자 정보)
            Member member = memberRepository.findByEmail(userId);
            //나중에 NickName으로 변경

            if (member != null) {
                BoardComment boardComment = new BoardComment();
                boardComment.setBoard(board); // 댓글이 속한 게시물 설정
                boardComment.setContent(content);
                boardComment.setMember(member); // 댓글 작성자 설정

                commentRepository.save(boardComment);
            } else {
                // 사용자 정보를 찾을 수 없을 경우 처리
                throw new EntityNotFoundException("User not found");
            }
        }
    }

    public CommentDto createCommentDto(String content) {
        CommentDto commentDto = new CommentDto();
        commentDto.setContent(content);

        // 사용자의 user_id를 어떻게 가져올지에 따라 구체적인 코드 작성

        // 예시: 현재 Spring Security를 사용하는 경우, Authentication 객체에서 user_id를 추출
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String userId = authentication.getName();
            commentDto.setUserId(userId);
        }

        return commentDto;
    }

    // 추가적인 댓글 관련 로직을 구현할 수 있습니다.
}
