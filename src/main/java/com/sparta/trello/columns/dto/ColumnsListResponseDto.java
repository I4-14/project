package com.sparta.trello.columns.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ColumnsListResponseDto {
    private final List<ColumnsListResponseData> columnsList;

    public ColumnsListResponseDto(List<ColumnsListResponseData> columnsList) {
        this.columnsList = columnsList;
    }
}
