package com.sparta.trello.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateCardStatusRequestDto {

  private Long columnId;
  private String cardStatus;
}
