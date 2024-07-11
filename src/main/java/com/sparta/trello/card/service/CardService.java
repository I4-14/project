package com.sparta.trello.card.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.card.dto.CardCreateRequestDto;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardUpdateCardStatusRequestDto;
import com.sparta.trello.card.dto.CardUpdateRequestDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.columns.entity.Columns;
import com.sparta.trello.columns.repository.ColumnsRepository;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;
  private final ColumnsRepository columnsRepository;

  @PersistenceContext
  private EntityManager entityManager;

  @Transactional(readOnly = true)
  public List<CardResponseDto> getAllCards(Long id) {
    List<Card> cards = cardRepository.findAll();
    List<CardResponseDto> cardDtoList = new ArrayList<>();
    for(Card card : cards) {
      CardResponseDto responseDto = new CardResponseDto(card);
      cardDtoList.add(responseDto);
    }
    return cardDtoList;
  }

  @Transactional(readOnly = true)
  public CardResponseDto getCardDetailsById(Long id) {
    Columns column = findColumnById(id);
  }

  @Transactional
  public CardResponseDto createCard(Long id, CardCreateRequestDto requestDto, User user) {
    Columns columns = findColumnById(id);
    Card card = cardRepository.save(new Card(requestDto, columns, user));
    return new CardResponseDto(card, user);
  }

  @Transactional
  public CardResponseDto updateCard(Long cardId, CardUpdateRequestDto requestDto, User user) {
    Card card = findCardById(cardId);
    card.checkUser(user);
    card.updateCard(requestDto);
    return new CardResponseDto(card, user);
  }

  @Transactional
  public CardResponseDto updateCardStatus(Long id, Long cardId, CardUpdateCardStatusRequestDto requestDto, User user) {
    Card card = findCardById(cardId);
    card.updateCardStatus(requestDto);



    return
  }

  @Transactional
  public void deleteCard(Long id, User user) {
    Card card = findCardById(id);
    card.checkUser(user);
    cardRepository.delete(card);
  }

  // 나중에 columns service에서 가져오기 수정필요**
  private Columns findColumnById(Long id) {
    return columnsRepository.findById(id).orElseThrow(
        ()-> new IllegalArgumentException("해당 컬럼을 찾을 수 없습니다."));
  }

  private Card findCardById(Long id) {
    return cardRepository.findById(id).orElseThrow(
        ()-> new IllegalArgumentException("해당 카드를 찾을 수 없습니다."));
  }

  private Columns findColumnByCardId(Columns column) {
    JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
    QCard card =
  }

}
