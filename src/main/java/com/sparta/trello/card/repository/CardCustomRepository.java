package com.sparta.trello.card.repository;

import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import java.util.List;

public interface CardCustomRepository {

  List<CardResponseDto> findCardsInColumn(CardSearchCondDto searchCond);
}
