package com.sparta.trello.comment.repository;

import com.sparta.trello.comment.dto.CommentResponseDto;
import java.util.List;

public interface CommentCustomRepository {

  List<CommentResponseDto> findCommentByCardIdOrderByCreatedAtDesc(int page, int amount, Long cardId);
}
