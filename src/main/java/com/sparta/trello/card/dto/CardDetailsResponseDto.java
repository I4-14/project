package com.sparta.trello.card.dto;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.entity.Comment;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardDetailsResponseDto {

  private String title;
  private String content;
  private String dueDate;
  private CategoryEnum cardStatus;
  private List<CommentResponseDto> commentList;
//  private String username;

  public CardDetailsResponseDto(Card card, List<CommentResponseDto> commentList) {
    this.title = card.getTitle();
    this.content = card.getContent();
    this.dueDate = card.getDueDate();
    this.cardStatus = card.getCardStatus();
    this.commentList = commentList;
  }
}
