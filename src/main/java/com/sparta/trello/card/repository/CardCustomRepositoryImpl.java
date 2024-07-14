package com.sparta.trello.card.repository;

import static com.sparta.trello.card.entity.QCard.card;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.card.dto.CardResponseDto;
import com.sparta.trello.card.dto.CardSearchCondDto;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.entity.QCard;
import com.sparta.trello.columns.entity.CategoryEnum;
import com.sparta.trello.columns.entity.QColumns;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CardCustomRepositoryImpl implements CardCustomRepository{

  private final JPAQueryFactory queryFactory;

  public List<CardResponseDto> findCardsInColumn(CardSearchCondDto searchCond) {
    QColumns column = QColumns.columns;
    QCard card = QCard.card;

    List<CardResponseDto> cardList = queryFactory
        .select(Projections.constructor(CardResponseDto.class, card))
        .from(card)
        .innerJoin(card.columns, column)
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
    BooleanExpression result = null;
    if(!searchCond.getUsername().isEmpty()){
      return result = card.user.name.eq(searchCond.getUsername());
    }
    if(searchCond.getCardStatus() != null) {
      return result = card.cardStatus.eq(CategoryEnum.valueOf(searchCond.getCardStatus()));
    }
    return null;
  }
}
