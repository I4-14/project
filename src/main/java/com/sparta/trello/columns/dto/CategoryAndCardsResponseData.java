package com.sparta.trello.columns.dto;

import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.columns.entity.Columns;
import lombok.Getter;

import java.util.List;

@Getter
public class CategoryAndCardsResponseData {
    private final Long id;
    private final CategoryEnum category;
    private final List<CardResponseDto> cards;


    public CategoryAndCardsResponseData(Columns columns, List<CardResponseDto> cards) {
        this.id = columns.getId();
        this.category = columns.getCategory();
        this.cards = cards;
    }
}
