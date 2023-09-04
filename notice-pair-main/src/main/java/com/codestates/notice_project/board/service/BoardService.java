package com.codestates.notice_project.board.service;

import com.codestates.notice_project.board.entity.Board;
import com.codestates.notice_project.board.repository.BoardRepository;

import com.codestates.notice_project.exception.BusinessLogicException;
import com.codestates.notice_project.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    // 게시글 게시
    public Board createBoard(Board board){
        return boardRepository.save(board);
    }

    // 게시글 수정
    public Board updateBoard(Board board){

        // 등록되어 있는 게시물인지 확인
        Board findBoard = findVerifiedBoard(board.getBoardId());

        // 등록되어 있으면, 수정 사항 확인
        Optional.ofNullable(board.getComment()).ifPresent(comment -> findBoard.setComment(comment));
        Optional.ofNullable(board.getBoardStatus()).ifPresent(status -> findBoard.setBoardStatus(status));

        // 수정 사항 저장
        return boardRepository.save(findBoard);
    }

    // 게시글 조회
    @Transactional(readOnly = true)
    public Board findBoard(long boardId){
        return findVerifiedBoard(boardId);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public Page<Board> findBoards(int page, int size){
        return boardRepository
                .findAll(PageRequest.of(page, size, Sort.by("boardId").descending()));
    }

    // 게시글 삭제
    public void deleteBoard(long boardId){
       Board board = findVerifiedBoard(boardId);
       boardRepository.delete(board);
    }

    // 존재하는 게시물인지 확인 --> for 수정, 조회, 삭제
    private Board findVerifiedBoard(long boardId){
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        Board findBoard = optionalBoard.orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOARD_NOT_FOUND));

        return findBoard;
    }

}
