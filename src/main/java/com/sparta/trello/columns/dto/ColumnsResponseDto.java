package com.sparta.trello.columns.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ColumnsResponseDto {
    private String msg;
    private int statuscode;
    private ColumnsResponseData data;
}


