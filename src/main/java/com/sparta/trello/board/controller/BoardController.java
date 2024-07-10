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

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    // 임시 유저
    User temUser = new User();
    /**
     *      보드 목록 조회
     *      get
     *      /api/boards
     * */


    /**
     *      보드 생성
     *      post
     *      /api/boards
     *      {
     *          ”title” : “보드제목”,
     *          "description": "보드설명"
     *      }
     * */
    // todo @AuthenticationPrincipal으로 유저 정보도 같이 전달
    public ResponseEntity<ApiResponse<BoardResponseDto>> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto,temUser);
        ApiResponse<BoardResponseDto> response = new ApiResponse<>("보드 생성 성공","201",boardResponseDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     *      보드 수정
     *      put
     *      /api/boards/{id}
     *      {
     *          ”title” : “보드제목”,
     *          "description": "보드설명"
     *      }
     * */




    /**
     *      보드 삭제
     *      delete
     *      /api/boards/{id}
     *
     * */


    /**
     *      보드 초대
     *      post
     *      /api/boards/{id}/users
     *      {
     *          ”name” : “사용자이름”
     *      }
     * */
}
