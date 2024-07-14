package com.sparta.trello.boardworkspace.controller;

import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import com.sparta.trello.boardworkspace.dto.InviteRequestDto;
import com.sparta.trello.boardworkspace.dto.InviteResponseDto;
import com.sparta.trello.boardworkspace.service.InviteService;
import com.sparta.trello.common.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    /**
     *
     *      초대 목록 보기
     *
     */
    @GetMapping("/invite/{id}")
    public ResponseEntity<ApiResponse<List<InvitationListDto>>> getInvitationList(@PathVariable("id") Long id) {
        List<InvitationListDto> responseDto= inviteService.getInvitationList(id);
        ApiResponse<List<InvitationListDto>> response = new ApiResponse("보드 초대 목록보기", "201", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /**
     *
     * 초대에 승락하기
     */

    @PutMapping("/accept/invite/{workspaceId}")
    public ResponseEntity<ApiResponse<InviteResponseDto>> acceptToInvitation (@PathVariable("workspaceId") Long workspaceId) {
        inviteService.acceptInvitation(workspaceId);
        ApiResponse response = new ApiResponse("보드 초대 승락", "201", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * 초대에 거절하기
     *
     */

    @PutMapping("/reject/invite/{workspaceId}")
    public ResponseEntity<ApiResponse<InviteResponseDto>> rejectToInvitation (@PathVariable("workspaceId") Long workspaceId) {
        inviteService.rejectInvitation(workspaceId);
        ApiResponse response = new ApiResponse("보드 초대 거절", "201", null);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


    /**
     *  보드에 있는 사람 목록 보기
     */
    @GetMapping("/boards/{boardId}/member")
    public ResponseEntity<ApiResponse<List<String>>> getBoardMember (@PathVariable("boardId") Long boardId) {
        List<String> boardMember = inviteService.getBoardMember(boardId);
        ApiResponse response = new ApiResponse("보드 초대한 사람 목록", "200", boardMember);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
