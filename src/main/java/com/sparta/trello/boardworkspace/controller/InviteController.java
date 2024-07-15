package com.sparta.trello.boardworkspace.controller;

import com.sparta.trello.auth.security.UserDetailsImpl;
import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import com.sparta.trello.boardworkspace.dto.InviteRequestDto;
import com.sparta.trello.boardworkspace.dto.InviteResponseDto;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import com.sparta.trello.boardworkspace.service.InviteService;
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
public class InviteController {
    private final InviteService inviteService;

    /**
     *      보드 초대
     *      post
     *      /api/boards/{boardId}/users
     *      {
     *          ”name” : “사용자이름”
     *      }
     * */
    @PostMapping("/boards/{boardId}/users")
    public ResponseEntity<ApiResponse<InviteResponseDto>> inviteBoard(@PathVariable("boardId") Long boardId, @RequestBody InviteRequestDto boardRequestDto) {

        InviteResponseDto inviteResponseDto = inviteService.inviteBoard(boardId,boardRequestDto);

        ApiResponse<InviteResponseDto> response = new ApiResponse("보드 초대 성공", "201", inviteResponseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/boards/{boardId}/users/{userId}")
    public ResponseEntity<ApiResponse<InviteResponseDto>> deleteMember(@PathVariable("boardId") Long boardId, @PathVariable("userId") Long userId) {
        inviteService.deleteMember(boardId,userId);
        ApiResponse<InviteResponseDto> response = new ApiResponse("보드 맴버 삭제 성공", "204", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /**
     *  보드에 있는 사람 목록 보기
     */
    @GetMapping("/boards/{boardId}/member")
    public ResponseEntity<ApiResponse<List<MemberDto>>> getBoardMember (@PathVariable("boardId") Long boardId) {
        List<MemberDto> boardMember = inviteService.getBoardMember(boardId);
        ApiResponse response = new ApiResponse("보드 멤버 목록 조회 성공", "200", boardMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
