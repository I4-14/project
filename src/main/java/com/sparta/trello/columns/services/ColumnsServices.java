package com.sparta.trello.columns.services;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.dto.*;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ColumnsServices {
    private final ColumnsRepository columnsRepository;
    private final BoardRepository boardRepository;
    public ColumnsResponseDto createColumns(Long boardId, ColumnsRequestDto requestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorEnum.BOARD_NOT_FOUND));
        Long maxOrderNum = columnsRepository.findMaxOrderNum().orElse(0L);
        Columns columns = Columns.builder()
                .board(board)
                .category(CategoryEnum.valueOf(requestDto.getCategory()))
                .orderNum(maxOrderNum + 1L)
                .build();
        columns.checkUser(user);
        columnsRepository.save(columns);
        return new ColumnsResponseDto(columns.getCategory());
    }
    @Transactional
    public ColumnsResponseDto updateColumns(Long columnsId, ColumnsRequestDto requestDto, User user) {
        Columns columns = findColumnsById(columnsId);
        columns.checkUser(user);
        columns.updateComment(requestDto);
        return new ColumnsResponseDto(columns.getCategory());
    }
    public void deleteColumns(Long columnId, User user) {
        Long maxOrderNum = columnsRepository.findMaxOrderNum().orElse(0L);
        Columns columns = findColumnsById(columnId);
        columns.checkUser(user);
        Long currentOrderNum = columns.getOrderNum();
        List<Columns> betweenColumns = columnsRepository.findByBetweenColumnIdAndInFrontOfId(currentOrderNum - 1, maxOrderNum);
        betweenColumns.forEach(Columns::subtractOrderNum);

        columnsRepository.delete(columns);
    }

    @Transactional
    public void changeOrderNum(Long columnId, Long inFrontofId, User user) {
        Columns columns = findColumnsById(columnId);
        columns.checkUser(user);
        Long orginalOrderNum = columns.getOrderNum();
        Long destinationOrderNum = columnsRepository.findOrderNumById(inFrontofId).orElseThrow(() -> new CustomException(ErrorEnum.COLUMN_NOT_FOUND));

        if(orginalOrderNum > destinationOrderNum) {
            List<Columns> betweenColumns = columnsRepository.findByBetweenColumnIdAndInFrontOfId(destinationOrderNum, orginalOrderNum - 1);
            betweenColumns.forEach(Columns::addOrderNum);
            columns.updateOrderNum(destinationOrderNum);
        } else if(orginalOrderNum < destinationOrderNum) {
            List<Columns> betweenColumns = columnsRepository.findByBetweenColumnIdAndInFrontOfId(orginalOrderNum + 1, destinationOrderNum - 1);
            betweenColumns.forEach(Columns::subtractOrderNum);
            columns.updateOrderNum(destinationOrderNum - 1);
        } else {
            throw new CustomException(ErrorEnum.SAME_COLUMN);
        }
    }

    public Columns findColumnsById(Long id) {
        return columnsRepository.findById(id).orElseThrow(() -> new CustomException(ErrorEnum.COLUMN_NOT_FOUND));
    }

    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorEnum.BOARD_NOT_FOUND));
    }
    public ColumnsListResponseDto getColumnsList(Long id, User user) {
        Board board = findBoardById(id);
        List<Card> cards = new ArrayList<>();
        List<Columns> columnList = board.getColumnsList().stream().sorted(Comparator.comparing(Columns::getOrderNum)).toList();
        //columnList.get(0).checkUser(user);
        List<CardResponseDto> cardResponseDtos = new ArrayList<>();
        List<CategoryAndCardsResponseData> columns = new ArrayList<>();
        for (int i = 0; i < columnList.size(); i++) {
            cards.addAll(columnList.get(i).getCards().stream().sorted(Comparator.comparing(Card::getPosition)).toList());
            cardResponseDtos = cards.stream().map(card -> new CardResponseDto(card)).toList();
            columns.add(new CategoryAndCardsResponseData(columnList.get(i), cardResponseDtos));
            cards = new ArrayList<>();
            cardResponseDtos = new ArrayList<>();
        }
        List<ColumnsListResponseData> columnsListData = new ArrayList<>();
        for (int i = 0; i < columns.size(); i++) {
            columnsListData.add(new ColumnsListResponseData(columns.get(i)));
        }
        return new ColumnsListResponseDto(columnsListData);
    }

    public ColumnsResponseDto getColumnsById(Long id, User user) {
        Columns columns = findColumnsById(id);
        columns.checkUser(user);
        return new ColumnsResponseDto(columns.getCategory());
    }
}
