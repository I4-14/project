package com.sparta.trello.board.controller;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.service.BoardService;
import com.sparta.trello.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 임시 유저
    User temUser = new User();

    /**
     * 보드 목록 조회
     * get
     * /api/boards
     */
    @GetMapping("/boards")
    public ResponseEntity<ApiResponse<List<BoardResponseDto>>> getBoardlist(@RequestParam(value = "page", defaultValue = "1") int page,
                                                                            @RequestParam(value = "sortBy", defaultValue = "createdAt") String sortBy) {
        List<BoardResponseDto> boardList = boardService.getBoardList(page - 1, sortBy);
        ApiResponse<List<BoardResponseDto>> response = new ApiResponse<>("보드 목록 조회 성공", "200", boardList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * 보드 생성
     * post
     * /api/boards
     * {
     * ”title” : “보드제목”,
     * "description": "보드설명"
     * }
     */
    @PostMapping("/boards")
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);
        ApiResponse<BoardResponseDto> response = new ApiResponse<>("보드 생성 성공", "201", boardResponseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 보드 수정
     * put
     * /api/boards/{boardId}
     * {
     * ”title” : “보드제목”,
     * "description": "보드설명"
     * }
     */
    @PutMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponse<BoardResponseDto>> updateBoard(@PathVariable("boardId") Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.updateBoard(boardId, boardRequestDto);
        ApiResponse<BoardResponseDto> response = new ApiResponse<>("보드 수정 성공", "200", boardResponseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    /**
     * 보드 삭제
     * delete
     * /api/boards/{boardId}
     */
    @DeleteMapping("/boards/{boardId}")
    public ResponseEntity<ApiResponse> deleteBoard(@PathVariable("boardId") Long boardId) {
        Boolean check = boardService.deleteBoard(boardId, temUser);
        if (check.equals(true)) {
            ApiResponse<Void> response = new ApiResponse("보드 삭제 성공", "204", null);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {
            ApiResponse<Void> response = new ApiResponse("보드 삭제 실패", "400", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

    }



}
