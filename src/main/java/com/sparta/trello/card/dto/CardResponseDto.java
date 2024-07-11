package com.sparta.trello.card.dto;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.columns.entity.Columns;
import lombok.Getter;

@Getter
public class CardResponseDto {
  private String title;
  private String content;
  private String dueDate;
  private CategoryEnum cardStatus;
  private String username;

  public CardResponseDto(Card card, User user) {
    this.title = card.getTitle();
    this.cardStatus = card.getCardStatus();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.username = user.getName();
  }

  public CardResponseDto(Card card, Columns column, User user) {
    this.title = card.getTitle();
    this.cardStatus = column.getCategory();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.username = user.getName();
  }

}
