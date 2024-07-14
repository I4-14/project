package com.sparta.trello.card.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardSearchCondDto {

  private String username;
  private String cardStatus;

}
