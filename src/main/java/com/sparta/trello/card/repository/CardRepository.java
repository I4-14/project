package com.sparta.trello.card.repository;

import com.sparta.trello.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
