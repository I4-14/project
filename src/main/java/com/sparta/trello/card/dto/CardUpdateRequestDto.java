package com.sparta.trello.card.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardUpdateRequestDto {

  @NotBlank(message = "카드 제목을 입력해주세요.")
  private String title;
  private String content;
  private String dueDate;
  private String username;

}
