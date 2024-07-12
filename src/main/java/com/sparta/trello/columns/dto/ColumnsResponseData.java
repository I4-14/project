package com.sparta.trello.columns.dto;

import com.sparta.trello.columns.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ColumnsResponseData {
    private CategoryEnum category;
}
