package com.sparta.trello.card.dto;

import com.sparta.trello.columns.entity.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CardCreateRequestDto {

  @NotBlank(message = "카드 제목을 입력해주세요.")
  private String title;
  @NotBlank(message = "카드 상태를 입력해주세요.")
  private CategoryEnum cardStatus;
  private String content;
  private String dueDate;
  private Long userId;

}
