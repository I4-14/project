package com.sparta.trello.comment.entity;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.comment.dto.CommentRequestDto;
import com.sparta.trello.common.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String message;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  private Card card;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "user_id")
//  private User user;

  public Comment(CommentRequestDto requestDto, Card card){
    this.message = requestDto.getMessage();
    this.card = card;
//    this.user = user;
  }
}
