package com.sparta.trello.board.controller;

import com.sparta.trello.auth.security.UserDetailsImpl;
import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;


    @GetMapping("/boards")
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getBoardlist(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy,
                                                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<BoardResponseDto> boardList = boardService.getBoardList(page - 1, sortBy,userDetails.getUser());

        ApiResponse response = ApiResponse.builder()
                .msg("보드 목록 조회 성공")
                .statuscode(String.valueOf(HttpStatus.OK.value()))
                .data(boardList)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/boards")
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);
        ApiResponse response = ApiResponse.builder()
                .msg("보드 생성 성공")
                .statuscode(String.valueOf(HttpStatus.CREATED.value()))
                .data(boardResponseDto)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    @PutMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, boardRequestDto);
        ApiResponse response = ApiResponse.builder()
                .msg("보드 수정 성공")
                .statuscode(String.valueOf(HttpStatus.OK.value()))
                .data(boardResponseDto)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponse> deleteBoard(@PathVariable("boardId") Long boardId) {
        boardService.deleteBoard(boardId);
        ApiResponse response = ApiResponse.builder()
                .msg("보드 삭제 성공")
                .statuscode(String.valueOf(HttpStatus.NO_CONTENT.value()))
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
