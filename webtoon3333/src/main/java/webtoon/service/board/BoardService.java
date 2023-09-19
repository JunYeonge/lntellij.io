package webtoon.service.board;



import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import webtoon.config.CustomUserDetails;
import webtoon.dto.board.BoardDto;
import webtoon.entity.board.Board;
import webtoon.entity.member.Member;
import webtoon.repository.board.BoardRepository;
import webtoon.repository.member.MemberRepository;

import java.util.Optional;


@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    public void save(BoardDto boardDto) {
        Board board = Board.toSaveEntity(boardDto);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String info = userDetails.getEmail();
        Member userId = memberRepository.findByEmail(info);
        board.setMember(userId);
        boardRepository.save(board);
    }

    public Page<BoardDto> findAll(Pageable pageable) {
        Page<Board> boardPage = boardRepository.findAll(pageable);
        return boardPage.map(BoardDto::toBoardDto);
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    public BoardDto findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            BoardDto boardDto = BoardDto.toBoardDto(board);
            return boardDto;
        } else {
            return null;
        }
    }

    public BoardDto update(BoardDto boardDto) {
        Board board = Board.toUpdateEntity(boardDto);
        boardRepository.save(board);
        return findById(board.getId());
    }

    @Transactional
    public void delete(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    public Page<BoardDto> search(String keyword, Pageable pageable) {
        Page<Board> boardPage = boardRepository. findByBoardTitleContainingIgnoreCase(keyword, pageable);
        return boardPage.map(BoardDto::toBoardDto);

    }
}

