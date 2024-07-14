package com.sparta.trello.comment.service;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.comment.dto.CommentRequestDto;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.entity.Comment;
import com.sparta.trello.comment.repository.CommentRepository;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CardRepository cardRepository;
  private final UserRepository userRepository;

  @Transactional
  public CommentResponseDto createComment(Long cardId, CommentRequestDto requestDto, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorEnum.USER_NOT_AUTHENTICATED));
    Card card = cardRepository.findCardById(cardId);
    Comment comment = commentRepository.save(new Comment(requestDto, card, user));
    return new CommentResponseDto(comment, user);
  }

  public List<CommentResponseDto> getComments(int page, int amount, Long cardId, Long userId) {
    User user = userRepository.findById(userId).orElseThrow(()-> new CustomException(ErrorEnum.USER_NOT_AUTHENTICATED));
    Card card = cardRepository.findCardById(cardId);
    List<CommentResponseDto> comments = commentRepository.findCommentByCardIdOrderByCreatedAtDesc(page, amount, cardId);
    if(comments.isEmpty()) {
      throw new IllegalArgumentException("현재 카드의 댓글이 존재하지 않습니다.");
    }
    return comments;
  }

}
