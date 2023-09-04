package com.codestates.notice_project.board.mapper;

import com.codestates.notice_project.board.dto.BoardDto;
import com.codestates.notice_project.board.entity.Board;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BoardMapper {
    Board boardPostDtoToBoard(BoardDto.Post boardPostDto);
    Board boardPatchDtoToBoard(BoardDto.Patch boardPatchDto);
    BoardDto.Response boardToBoardResponseDto(Board board);
    List<BoardDto.Response> boardToBoardResponseDtos(List<Board> boards);
}
