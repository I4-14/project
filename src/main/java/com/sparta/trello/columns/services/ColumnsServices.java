package com.sparta.trello.columns.services;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import com.sparta.trello.columns.dto.ColumnsRequestDto;
import com.sparta.trello.columns.dto.ColumnsResponseDto;
import com.sparta.trello.columns.dto.ResponseData;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ColumnsServices {
    private final ColumnsRepository columnsRepository;
    private final BoardRepository boardRepository;
    public ColumnsResponseDto createColumns(Long boardId, ColumnsRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
        Columns columns = Columns.builder()
                .board(board)
                .category(CategoryEnum.valueOf(requestDto.getCategory()))
                .build();
        columnsRepository.save(columns);
        ResponseData responseData = new ResponseData(columns.getCategory());
        return createResponseDto("컬럼 생성성공", HttpStatus.CREATED, responseData);
    }
    @Transactional
    public ColumnsResponseDto updateColumns(Long columnsId, ColumnsRequestDto requestDto) {
        Columns columns = findById(columnsId);
        columns.updateComment(requestDto);
        ResponseData responseData = new ResponseData(columns.getCategory());
        return createResponseDto("컬럼 수정성공", HttpStatus.OK, responseData);
    }
    public ColumnsResponseDto deleteColumns(Long columnId) {
        Columns columns = findById(columnId);
        columnsRepository.delete(columns);
        return createResponseDto("컬럼 삭제성공", HttpStatus.NO_CONTENT);
    }

    public Columns findById(Long id) {
        return columnsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));
    }

    public ColumnsResponseDto createResponseDto(String msg, HttpStatus status, ResponseData responseData) {
        return ColumnsResponseDto.builder()
                .msg(msg)
                .statuscode(status.value())
                .data(responseData)
                .build();
    }
    public ColumnsResponseDto createResponseDto(String msg, HttpStatus status) {
        return ColumnsResponseDto.builder()
                .msg(msg)
                .statuscode(status.value())
                .build();
    }
}
