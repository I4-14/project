package com.sparta.trello.card.repository;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CardCustomRepository {

 default Card findCardById(Long id) {
   return findById(id).orElseThrow(
       ()-> new CustomException(ErrorEnum.CARD_NOT_FOUND));
 }
}
