package com.sparta.trello.comment.controller;

import com.sparta.trello.auth.security.UserDetailsImpl;
import com.sparta.trello.comment.dto.CommentRequestDto;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.service.CommentService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @PostMapping("/{cardId}/comments")
  public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long cardId,
      @RequestBody @Valid CommentRequestDto requestDto) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commentService.createComment(cardId, requestDto));
  }

  @GetMapping("/{cardId}/comments")
  public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam int page, @RequestParam(defaultValue = "5") int amount, @PathVariable Long cardId) {
    return ResponseEntity.status(HttpStatus.OK).body(commentService.getComments(page - 1, amount, cardId));
  }

}
