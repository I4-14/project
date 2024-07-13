package com.sparta.trello.card.service;

import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardDetailsResponseDto;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.dto.CardUpdateCardStatusRequestDto;
import com.sparta.trello.card.dto.CardUpdateRequestDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.services.ColumnsServices;
import com.sparta.trello.comment.repository.CommentRepository;
import com.sparta.trello.comment.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final ColumnsServices columnsServices;
  private final CommentService commentService;
  private final CommentRepository commentRepository;

  public List<CardResponseDto> getAllCards(CardSearchCondDto searchCond) {
    List<CardResponseDto> cardList = cardRepository.findCardsInColumn(searchCond);
    return cardList;
  }

  public CardDetailsResponseDto getCardDetailsById(Long cardId) {
    Card card = findCardById(cardId);
//    List<CommentResponseDto> commentDtos = commentRepository.findCommentByCardIdOrderByCreatedAtDesc(page, amount, cardId);
   return new CardDetailsResponseDto(card);
  }

  @Transactional
  public CardResponseDto createCard(Long id, CardCreateRequestDto requestDto) {
    Columns columns = columnsServices.findById(id);
    int position= getNextPosition(columns.getId());
    Card card = cardRepository.save(new Card(requestDto, columns));
    card.setPosition(position);
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
    Columns column = columnsServices.findById(requestDto.getColumnId());
    card.updateCardStatus(column, requestDto);
    return new CardResponseDto(card);
  }

  @Transactional
  public void moveCardToPosition(Long cardId, int newPosition) {
    Card card = findCardById(cardId);
    Columns column = card.getColumns();
    int currentPosition = card.getPosition();

    List<Card> cards = cardRepository.findByColumnIdOrderByPositionAsc(column.getId());

    if (newPosition < 0 || newPosition >= cards.size()) {
      throw new IllegalArgumentException("잘못된 카드 위치번호 입니다.");
    }

    if(currentPosition < newPosition) {
      for(Card c : cards) {
        if (c.getPosition() > currentPosition && c.getPosition() <= newPosition) {
          c.setPosition(c.getPosition() - 1);
          cardRepository.save(c);
        }
      }
    } else {
      for(Card c : cards) {
        if(c.getPosition() >= newPosition && c.getPosition() < currentPosition) {
          c.setPosition(c.getPosition() + 1);
          cardRepository.save(c);
        }
      }
    }
    updateCardPosition(cardId, newPosition);
    reorderCards(column.getId());
  }

  @Transactional
  public void deleteCard(Long cardId) {
    Card card = findCardById(cardId);
//    card.checkUser(user);
    cardRepository.delete(card);
  }

  private Card findCardById(Long cardId) {
    return cardRepository.findCardById(cardId);
  }

  private int getNextPosition(Long id) {
    List<Card> cards = cardRepository.findByColumnIdOrderByPositionAsc(id);
    if (cards.isEmpty()) {
      return 0;
    }
    return cards.get(cards.size() - 1).getPosition() + 1;
  }

  private void reorderCards(Long id) {
    List<Card> cards = cardRepository.findByColumnIdOrderByPositionAsc(id);
    int position = 0;
    for(Card card : cards) {
      if (card.getPosition() != position) {
        updateCardPosition(card.getId(), position);
      }
      position++;
    }
  }

  private void updateCardPosition(Long cardId, int position) {
    Card card = cardRepository.findCardById(cardId);
    card.updatePosition(position);
    cardRepository.save(card);
  }

}
