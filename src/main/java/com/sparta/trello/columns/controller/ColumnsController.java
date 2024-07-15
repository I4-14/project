package com.sparta.trello.columns.controller;

import com.sparta.trello.auth.security.UserDetailsImpl;
import com.sparta.trello.columns.dto.ColumnsListResponseDto;
import com.sparta.trello.columns.dto.ColumnsRequestDto;
import com.sparta.trello.columns.dto.ColumnsResponseDto;
import com.sparta.trello.columns.services.ColumnsServices;
import com.sparta.trello.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnsController {
    private final ColumnsServices columnsServices;
    @GetMapping("/boards/{id}")
    public ResponseEntity<ApiResponse<ColumnsListResponseDto>> getColumnsList(@PathVariable("id") Long id) {
        ColumnsListResponseDto responseDto = columnsServices.getColumnsList(id);
        ApiResponse<ColumnsListResponseDto> response = new ApiResponse<>("컬럼, 카드조회 성공", HttpStatus.OK.value() + "", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PostMapping("/boards/{id}/columns")
    public ResponseEntity<ApiResponse<ColumnsResponseDto>> createColumns(@PathVariable("id") Long id, @RequestBody ColumnsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto responseDto = columnsServices.createColumns(id, requestDto, userDetails.getUser());
        ApiResponse<ColumnsResponseDto> response = new ApiResponse<>("컬럼 생성성공", HttpStatus.CREATED.value() + "", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/columns/{id}")
    public ResponseEntity<ApiResponse<ColumnsResponseDto>> getColumnsById(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto responseDto = columnsServices.getColumnsById(id, userDetails.getUser());
        ApiResponse<ColumnsResponseDto> response = new ApiResponse<>("컬럼 조회성공", HttpStatus.OK.value() + "", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/columns/{id}")
    public ResponseEntity<ApiResponse<ColumnsResponseDto>> updateColumns(@PathVariable("id") Long id, @RequestBody ColumnsRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        ColumnsResponseDto responseDto = columnsServices.updateColumns(id, requestDto, userDetails.getUser());
        ApiResponse<ColumnsResponseDto> response = new ApiResponse<>("컬럼 수정성공", HttpStatus.OK.value() + "", responseDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/columns/{id}")
    public ResponseEntity<ApiResponse> deleteColumns(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnsServices.deleteColumns(id, userDetails.getUser());
        ApiResponse<Void> response = new ApiResponse<>("컬럼 삭제성공", HttpStatus.NO_CONTENT.value() + "", null);
        return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
    }
    @PostMapping("/columns/{id}/order/{destinationId}")
    public ResponseEntity<ApiResponse> changeOrderColumns(@PathVariable("id") Long id, @PathVariable("destinationId") Long destinationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        columnsServices.changeOrderNum(id, destinationId, userDetails.getUser());
        ApiResponse<Void> response = new ApiResponse<>("순서변경 성공", HttpStatus.OK.value() + "", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
