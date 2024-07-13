package com.sparta.trello.card.controller;

import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardDetailsResponseDto;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.dto.CardUpdateCardStatusRequestDto;
import com.sparta.trello.card.dto.CardUpdateRequestDto;
import com.sparta.trello.card.service.CardService;
import com.sparta.trello.common.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class CardController {

  private final CardService cardService;

  @GetMapping("/cards")
  public ResponseEntity<ApiResponse<List<CardResponseDto>>> getAllCards(@RequestBody CardSearchCondDto searchCond) {
    List<CardResponseDto> cardList = cardService.getAllCards(searchCond);
    ApiResponse<List<CardResponseDto>> response = new ApiResponse<>("카드 전체조회 성공", "200", cardList);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @GetMapping("/cards/{cardId}")
  public ResponseEntity<ApiResponse<CardDetailsResponseDto>> getCardDetailsById(@PathVariable Long cardId) {
    CardDetailsResponseDto cardDetails = cardService.getCardDetailsById(cardId);
    ApiResponse<CardDetailsResponseDto> response = new ApiResponse<>("카드 전체조회 성공", "200", cardDetails);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PostMapping("/{id}/cards")
  public ResponseEntity<ApiResponse<CardResponseDto>> createCard(@PathVariable("id") Long id, @Valid @RequestBody CardCreateRequestDto requestDto) {
    CardResponseDto card = cardService.createCard(id, requestDto);
    ApiResponse<CardResponseDto> response = new ApiResponse<>("카드 추가 성공", "201", card);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @PutMapping("/cards/{cardId}")
  public ResponseEntity<ApiResponse<CardResponseDto>> updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto requestDto) {
    CardResponseDto card = cardService.updateCard(cardId, requestDto);
    ApiResponse<CardResponseDto> response = new ApiResponse<>("카드 수정 성공", "200", card);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/cards/{cardId}/status")
  public ResponseEntity<ApiResponse<CardResponseDto>> updateCardStatus(@PathVariable Long cardId, @RequestBody CardUpdateCardStatusRequestDto requestDto) {
    CardResponseDto cardStatus = cardService.updateCardStatus(cardId, requestDto);
    ApiResponse<CardResponseDto> response = new ApiResponse<>("카드상태 수정 성공", "200", cardStatus);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @PutMapping("/cards/{cardId}/position")
  public ResponseEntity<ApiResponse<Integer>> moveCardToPosition(@PathVariable Long cardId, @RequestParam int newPosition) {
    cardService.moveCardToPosition(cardId, newPosition - 1);
    ApiResponse<Integer> response = new ApiResponse<>("카드위치 변경 성공", "200", newPosition);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  @DeleteMapping("/cards/{id}")
  public ResponseEntity<ApiResponse<Long>> deleteCard(@PathVariable Long id) {
  cardService.deleteCard(id);
  ApiResponse<Long> response = new ApiResponse<>("카드 삭제 성공", "200", id);
  return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
