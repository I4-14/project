package com.sparta.trello.card.controller;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardResponseDto;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/columns")
@RequiredArgsConstructor
public class CardController {

  private CardService cardService;

  @GetMapping("/{id}/cards")
  public ResponseEntity<List<CardResponseDto>> getAllCards(@PathVariable Long id) {
    List<CardResponseDto> cardList = cardService.getAllCards(id);
    return new ResponseEntity<>(cardList, HttpStatus.OK);
  }

  @GetMapping("/{id}/cards/{cardId}")
  public ResponseEntity<CardResponseDto> getCardDetailsById(@PathVariable Long cardId, @PathVariable Long id) {
    CardResponseDto responseDto = cardService.getCardDetailsById(id);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PostMapping("/{id}/cards")
  public ResponseEntity<CardResponseDto> createCard(@PathVariable Long id, @Valid @RequestBody CardCreateRequestDto requestDto, User user) {
    CardResponseDto responseDto = cardService.createCard(id, requestDto, user);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping("/cards/{cardId}")
  public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto requestDto, User user) {
    CardResponseDto responseDto = cardService.updateCard(cardId, requestDto, user);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PutMapping("/{id}/cards/{cardId}")
  public ResponseEntity<CardResponseDto> updateCardStatus(@PathVariable Long id, @PathVariable Long cardId, @RequestBody CardUpdateCardStatusRequestDto requestDto, User user) {
    CardResponseDto responseDto = cardService.updateCardStatus(id, cardId, requestDto, user);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @DeleteMapping("/cards/{id}")
  public ResponseEntity<String> deleteCard(@PathVariable Long id, User user) {
  cardService.deleteCard(id, user);
  return new ResponseEntity<>("카드 삭제 성공", HttpStatus.OK);
  }

}
