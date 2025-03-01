package com.sparta.trello.card.dto;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.CategoryEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardResponseDto {

  private Long boardId;
  private Long columnId;
  private Long id;
  private int position;
  private String title;
  private String content;
  private String dueDate;
  private CategoryEnum cardStatus;
  private Long userId;
  private String username;

  public CardResponseDto(Card card) {
    this.boardId = card.getBoard().getId();
    this.columnId = card.getColumns().getId();
    this.id = card.getId();
    this.position = card.getPosition();
    this.title = card.getTitle();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.cardStatus = card.getCardStatus();
    this.userId = card.getUser().getId();
    this.username = card.getUser().getUsername();
  }

}
