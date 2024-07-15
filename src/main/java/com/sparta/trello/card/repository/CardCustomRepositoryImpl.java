package com.sparta.trello.card.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.entity.QCard;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.trello.card.entity.QCard.card;
import static com.sparta.trello.columns.entity.QColumns.columns;

@Repository
@RequiredArgsConstructor
public class CardCustomRepositoryImpl implements CardCustomRepository{

  private final JPAQueryFactory queryFactory;

  public List<CardResponseDto> findCardsInColumn(CardSearchCondDto searchCond) {

    if(searchCond.getUsername().isEmpty()){
      throw new CustomException(ErrorEnum.USER_NOT_FOUND);
    }

    List<CardResponseDto> cardList = queryFactory
        .select(Projections.constructor(CardResponseDto.class, card))
        .from(card)
        .innerJoin(card.columns, columns)
        .where(isWhere(searchCond))
        .limit(10)
        .fetch();

    return cardList;
  }

  public List<Card> findByColumnIdOrderByPositionAsc(Long id) {
    QCard card = QCard.card;

    List<Card> cards = queryFactory
        .selectFrom(card)
        .where(card.columns.id.eq(id))
        .orderBy(card.position.asc())
        .fetch();

    return cards;
  }

  private BooleanExpression isWhere (CardSearchCondDto searchCond) {
    BooleanExpression result = card.user.username.eq(searchCond.getUsername());
    return result;
  }
}
