package com.sparta.trello.card.service;

import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.dto.CardUpdateCardStatusRequestDto;
import com.sparta.trello.card.dto.CardUpdateRequestDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final ColumnsRepository columnsRepository;

  public List<CardResponseDto> getAllCards(CardSearchCondDto searchCond) {
    List<CardResponseDto> cardList = cardRepository.findCardsInColumn(searchCond);
    return cardList;
  }

  public CardResponseDto getCardDetailsById(Long cardId) {
    Card card = findCardById(cardId);
   return new CardResponseDto(card);
  }

  @Transactional
  public CardResponseDto createCard(Long id, CardCreateRequestDto requestDto) {
    Columns columns = findColumnById(id);
    Card card = cardRepository.save(new Card(requestDto, columns));
    return new CardResponseDto(card);
  }

  @Transactional
  public CardResponseDto updateCard(Long cardId, CardUpdateRequestDto requestDto) {
    Card card = findCardById(cardId);
//    card.checkUser(user);
    card.updateCard(requestDto);
    return new CardResponseDto(card);
  }

  @Transactional
  public CardResponseDto updateCardStatus(Long cardId, CardUpdateCardStatusRequestDto requestDto) {
    Card card = findCardById(cardId);
    Columns column = findColumnById(requestDto.getColumnId());
    card.updateCardStatus(column, requestDto);
    return new CardResponseDto(card);
  }

//  @Transactional
//  public CardResponseDto moveCardToPosition(Long cardId, int newPosition) {
//    Card card = findCardById(cardId);
//    List<Card> cards = cardRepository.findAll();
//
//    int currentPosition = cards.indexOf(card);
//    int changePosition = newPosition - 1;
//
//    if (newPosition < 0 || newPosition >= cards.size()) {
//      throw new IllegalArgumentException("잘못된 카드 위치번호 입니다.");
//    }
//
//    if (currentPosition != newPosition) {
//      cards.remove(currentPosition);
//      cards.add(newPosition, card);
//      updateCardIndex(cards);
//    }
//    return new CardResponseDto(card);
//  }

  @Transactional
  public void deleteCard(Long id) {
    Card card = findCardById(id);
//    card.checkUser(user);
    cardRepository.delete(card);
  }

   // 나중에 columns service에서 가져오기 수정필요**
  private Columns findColumnById(Long id) {
    return columnsRepository.findById(id).orElseThrow(
        ()-> new IllegalArgumentException("해당 컬럼을 찾을 수 없습니다."));
  }

  private Card findCardById(Long id) {
    return cardRepository.findCardById(id);
  }

//  private void updateCardIndex(List<Card> cards) {
//    for (int i=0; i < cards.size(); i++) {
//      cards.get(i).setPosition(i + 1);
//    }
//  }
}
