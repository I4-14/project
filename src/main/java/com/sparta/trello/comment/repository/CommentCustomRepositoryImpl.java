package com.sparta.trello.comment.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.sparta.trello.auth.entity.QUser.user;
import static com.sparta.trello.comment.entity.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

  private final JPAQueryFactory queryFactory;

  public List<CommentResponseDto> findCommentByCardIdOrderByCreatedAtDesc(int page, int amount, Long cardId) {
    List<Comment> commentList = queryFactory
            .select(comment)
            .from(comment)
            .join(comment.user, user)
            .where(comment.card.id.eq(cardId))
            .orderBy(comment.createdAt.desc())
            .offset(page * amount)
            .limit(amount)
            .fetch();

    return commentList.stream()
            .map(c -> new CommentResponseDto(c, c.getUser()))
            .collect(Collectors.toList());
  }
}
