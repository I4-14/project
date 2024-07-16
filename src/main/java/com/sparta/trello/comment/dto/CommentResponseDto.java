package com.sparta.trello.comment.dto;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
  private Long cardId;
  private String message;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private String username;

  public CommentResponseDto(Comment comment, User user) {
    this.cardId = comment.getCard().getId();
    this.message = comment.getMessage();
    this.createdAt = comment.getCreatedAt();
    this.updatedAt = comment.getUpdatedAt();
    this.username = user.getName();
  }

}
