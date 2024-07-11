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

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnsServices {
    private final ColumnsRepository columnsRepository;
    private final BoardRepository boardRepository;
    public ColumnsResponseDto createColumns(Long boardId, ColumnsRequestDto requestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 보드입니다."));
        Long maxOrderNum = columnsRepository.findMaxOrderNum().orElse(0L);
        Columns columns = Columns.builder()
                .board(board)
                .category(CategoryEnum.valueOf(requestDto.getCategory()))
                .orderNum(maxOrderNum + 1L)
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
        Long maxOrderNum = columnsRepository.findMaxOrderNum().orElse(0L);
        Columns columns = findById(columnId);

        Long currentOrderNum = columns.getOrderNum();
        List<Columns> betweenColumns = columnsRepository.findByBetweenColumnIdAndInFrontOfId(currentOrderNum - 1, maxOrderNum);
        betweenColumns.forEach(Columns::subtractOrderNum);

        columnsRepository.delete(columns);
        return createResponseDto("컬럼 삭제성공", HttpStatus.NO_CONTENT);
    }

    @Transactional
    public ColumnsResponseDto changeOrderNum(Long columnId, Long inFrontofId) {
        Columns columns = findById(columnId);
        Long orginalOrderNum = columns.getOrderNum();
        Long destinationOrderNum = columnsRepository.findOrderNumById(inFrontofId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 컬럼입니다."));

        if(orginalOrderNum > destinationOrderNum) {
            List<Columns> betweenColumns = columnsRepository.findByBetweenColumnIdAndInFrontOfId(destinationOrderNum, orginalOrderNum - 1);
            betweenColumns.forEach(Columns::addOrderNum);
            columns.updateOrderNum(destinationOrderNum);
        } else if(orginalOrderNum < destinationOrderNum) {
            List<Columns> betweenColumns = columnsRepository.findByBetweenColumnIdAndInFrontOfId(orginalOrderNum + 1, destinationOrderNum - 1);
            betweenColumns.forEach(Columns::subtractOrderNum);
            columns.updateOrderNum(destinationOrderNum - 1);
        } else {
            throw new IllegalArgumentException("서로 같은 컬럼을 선택했습니다.");
        }

        return createResponseDto("순서 변경성공", HttpStatus.OK);
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
