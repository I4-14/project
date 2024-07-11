package com.sparta.trello.card.dto;

import com.sparta.trello.columns.entity.CategoryEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateCardStatusRequestDto {

  private CategoryEnum newCardStatus;
}
