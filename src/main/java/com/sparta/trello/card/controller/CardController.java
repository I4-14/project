package com.sparta.trello.card.controller;

import com.sparta.trello.card.dto.CardCreateRequestDto;
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
  public ResponseEntity<CardResponseDto> getCardDetailsById(@PathVariable Long cardId) {
    CardResponseDto responseDto = cardService.getCardDetailsById(cardId);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PostMapping("/{id}/cards")
  public ResponseEntity<CardResponseDto> createCard(@PathVariable Long id, @Valid @RequestBody CardCreateRequestDto requestDto) {
    CardResponseDto responseDto = cardService.createCard(id, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping("/cards/{cardId}")
  public ResponseEntity<CardResponseDto> updateCard(@PathVariable Long cardId, @RequestBody CardUpdateRequestDto requestDto) {
    CardResponseDto responseDto = cardService.updateCard(cardId, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PostMapping("/cards/{cardId}")
  public ResponseEntity<CardResponseDto> updateCardStatus(@PathVariable Long cardId, @RequestBody CardUpdateCardStatusRequestDto requestDto) {
    CardResponseDto responseDto = cardService.updateCardStatus(cardId, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @PostMapping("/cards/{cardId}/position")
  public ResponseEntity<CardResponseDto> updateCardStatus(@PathVariable Long cardId, @RequestParam int newPosition) {
    CardResponseDto responseDto = cardService.moveCardToPosition(cardId, newPosition);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @DeleteMapping("/cards/{id}")
  public ResponseEntity<String> deleteCard(@PathVariable Long id) {
  cardService.deleteCard(id);
  return new ResponseEntity<>("카드 삭제 성공", HttpStatus.OK);
  }

}
