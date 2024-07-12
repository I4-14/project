package com.sparta.trello.card.repository;

import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.entity.Card;
import java.util.List;

public interface CardCustomRepository {

  List<CardResponseDto> findCardsInColumn(CardSearchCondDto searchCond);

  List<Card> findByColumnIdOrderByPositionAsc(Long id);

}
