package com.sparta.trello.card.dto;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.comment.entity.Comment;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardResponseDto {
  
  private Long id;
  private int position;
  private String title;
  private String content;
  private String dueDate;
  private CategoryEnum cardStatus;
//  private String username;

  public CardResponseDto(Card card) {
    this.id = card.getId();
    this.position = card.getPosition();
    this.title = card.getTitle();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.cardStatus = card.getCardStatus();
  }

}
