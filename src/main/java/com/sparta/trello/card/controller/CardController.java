package com.sparta.trello.card.controller;

import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardDetailsResponseDto;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.dto.CardUpdateCardStatusRequestDto;
import com.sparta.trello.card.dto.CardUpdateRequestDto;
import com.sparta.trello.card.service.CardService;
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
  public ResponseEntity<List<CardResponseDto>> getAllCards(@RequestBody CardSearchCondDto searchCond) {
    List<CardResponseDto> cardList = cardService.getAllCards(searchCond);
    return new ResponseEntity<>(cardList, HttpStatus.OK);
  }

  @GetMapping("/cards/{cardId}")
  public ResponseEntity<CardDetailsResponseDto> getCardDetailsById(@PathVariable Long cardId) {
    CardDetailsResponseDto responseDto = cardService.getCardDetailsById(cardId);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PostMapping("/{id}/cards")
  public ResponseEntity<CardResponseDto> createCard(@PathVariable("id") Long id, @Valid @RequestBody CardCreateRequestDto requestDto) {
    CardResponseDto responseDto = cardService.createCard(id, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping("/cards/{cardId}")
  public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto requestDto) {
    CardResponseDto responseDto = cardService.updateCard(cardId, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PutMapping("/cards/{cardId}/status")
  public ResponseEntity<CardResponseDto> updateCardStatus(@PathVariable Long cardId, @RequestBody CardUpdateCardStatusRequestDto requestDto) {
    CardResponseDto responseDto = cardService.updateCardStatus(cardId, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PutMapping("/cards/{cardId}/position")
  public ResponseEntity<String> moveCardToPosition(@PathVariable Long cardId, @RequestParam int newPosition) {
    cardService.moveCardToPosition(cardId, newPosition - 1);
    return new ResponseEntity<>("순서이동 성공",HttpStatus.OK);
  }

  @DeleteMapping("/cards/{id}")
  public ResponseEntity<String> deleteCard(@PathVariable Long id) {
  cardService.deleteCard(id);
  return new ResponseEntity<>("카드 삭제 성공", HttpStatus.OK);
  }

}
