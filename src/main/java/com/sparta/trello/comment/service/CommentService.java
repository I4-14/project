package com.sparta.trello.comment.service;

import com.sparta.trello.card.entity.Card;
import com.sparta.trello.card.repository.CardRepository;
import com.sparta.trello.comment.dto.CommentRequestDto;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.entity.Comment;
import com.sparta.trello.comment.repository.CommentRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

  private final CommentRepository commentRepository;
  private final CardRepository cardRepository;

  @Transactional
  public CommentResponseDto createComment(Long cardId, CommentRequestDto requestDto) {
    Card card = cardRepository.findById(cardId).orElseThrow();
    Comment comment = commentRepository.save(new Comment(requestDto, card));
    return new CommentResponseDto(comment);
  }

  public List<CommentResponseDto> getComments(int page, int amount, Long cardId) {
    Pageable pageable = PageRequest.of(page, amount);

    Card card = cardRepository.findById(cardId).orElseThrow();
    Page<List<Comment>> comments = commentRepository.findByCardIdOrderByCreatedAtDesc(cardId, pageable);
    List<CommentResponseDto> commentList = new ArrayList<>();
    for (Comment comment : comments) {
      commentList.add(new CommentResponseDto(comment));
    }
    return commentList;
  }

}
