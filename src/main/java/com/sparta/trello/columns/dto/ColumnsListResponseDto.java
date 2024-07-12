package com.sparta.trello.columns.dto;

import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.Columns;
import lombok.Getter;

import java.util.List;

@Getter
public class ColumnsListResponseDto {
    private List<ColumnsListResponseData> columnsList;
}
