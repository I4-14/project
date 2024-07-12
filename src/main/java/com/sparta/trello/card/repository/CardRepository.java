package com.sparta.trello.card.repository;

import com.sparta.trello.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long>, CardCustomRepository {

 default Card findCardById(Long id) {
   return findById(id).orElseThrow(
       ()-> new IllegalArgumentException("해당 카드를 찾을 수 없습니다."));
 }
}
