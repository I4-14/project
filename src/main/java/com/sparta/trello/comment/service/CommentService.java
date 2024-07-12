package com.sparta.trello.comment.service;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.comment.dto.CommentRequestDto;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.entity.Comment;
import com.sparta.trello.comment.repository.CommentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CardRepository cardRepository;

  @Transactional
  public CommentResponseDto createComment(Long cardId, CommentRequestDto requestDto) {
    Card card = cardRepository.findCardById(cardId);
    Comment comment = commentRepository.save(new Comment(requestDto, card));
    return new CommentResponseDto(comment);
  }

  public List<CommentResponseDto> getComments(int page, int amount, Long cardId) {
    Card card = cardRepository.findCardById(cardId);
    List<CommentResponseDto> comments = commentRepository.findCommentByCardIdOrderByCreatedAtDesc(page, amount, cardId);
    return comments;
  }

}
