package com.sparta.trello.comment.controller;

import com.sparta.trello.comment.dto.CommentRequestDto;
import com.sparta.trello.comment.dto.CommentResponseDto;
import com.sparta.trello.comment.service.CommentService;
import com.sparta.trello.common.ApiResponse;
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
  public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(@PathVariable Long cardId,
      @RequestBody @Valid CommentRequestDto requestDto) {
    CommentResponseDto comment = commentService.createComment(cardId, requestDto);
    ApiResponse<CommentResponseDto> response = new ApiResponse<>("댓글 추가 성공", "201", comment);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
  }

  @GetMapping("/{cardId}/comments")
  public ResponseEntity<ApiResponse<List<CommentResponseDto>>> getComments(@RequestParam int page, @RequestParam(defaultValue = "5") int amount, @PathVariable Long cardId) {
    List<CommentResponseDto> comments = commentService.getComments(page - 1, amount, cardId);
    ApiResponse<List<CommentResponseDto>> response = new ApiResponse<>("댓글 조회 성공", "200", comments);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

}
