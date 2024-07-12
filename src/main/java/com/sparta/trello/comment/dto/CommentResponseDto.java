package com.sparta.trello.comment.dto;

import com.sparta.trello.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
  private String message;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
//  private String username;

  public CommentResponseDto(Comment comment) {
    this.message = comment.getMessage();
    this.createdAt = comment.getCreatedAt();
    this.updatedAt = comment.getUpdatedAt();
//    this.username = user.getName();
  }

}
