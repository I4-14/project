package com.sparta.trello.comment.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.card.entity.QCard;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.entity.QComment;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

  private final JPAQueryFactory queryFactory;

  public List<CommentResponseDto> findCommentByCardIdOrderByCreatedAtDesc(int page, int amount, Long cardId) {
    Pageable pageable = PageRequest.of(page, amount);
    QCard card = QCard.card;
    QComment comment = QComment.comment;

    List<CommentResponseDto> commentList = queryFactory
        .select(Projections.constructor(CommentResponseDto.class,
            comment,
            comment.user))
        .from(comment)
        .where(comment.card.id.eq(cardId))
        .leftJoin(comment.card, card)
        .orderBy(comment.createdAt.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch();

    return commentList;
  }
}
