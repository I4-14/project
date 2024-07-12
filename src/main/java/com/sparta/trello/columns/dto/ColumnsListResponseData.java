package com.sparta.trello.columns.dto;

import lombok.Getter;

@Getter
public class ColumnsListResponseData {
    private final CategoryAndCardsResponseData column;

    public ColumnsListResponseData(CategoryAndCardsResponseData column) {
        this.column = column;
    }
}
