package com.sparta.trello.columns.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ColumnsRequestDto {
    @NotBlank(message = "카테고리는 필수 입력입니다.")
    private String category;
}
