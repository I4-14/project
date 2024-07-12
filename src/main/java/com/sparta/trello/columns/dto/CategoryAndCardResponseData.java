package com.sparta.trello.columns.dto;

import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.columns.entity.CategoryEnum;

import java.util.List;

public class CategoryAndCardResponseData {
    private CategoryEnum category;
    List<CardResponseDto> cards;
}
