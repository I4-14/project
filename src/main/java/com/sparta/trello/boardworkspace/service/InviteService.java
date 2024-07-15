package com.sparta.trello.boardworkspace.service;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import com.sparta.trello.boardworkspace.dto.InviteRequestDto;
import com.sparta.trello.boardworkspace.dto.InviteResponseDto;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import com.sparta.trello.boardworkspace.entity.BoardWorkspace;
import com.sparta.trello.boardworkspace.entity.InvitationEnum;
import com.sparta.trello.boardworkspace.repository.BoardWorkspaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardWorkspaceRepository boardWorkspaceRepository;


    @Transactional
    public InviteResponseDto inviteBoard(Long boardId, InviteRequestDto inviteRequestDto) {
        Board boardEntity = boardRepository.findById(boardId).orElse(null);
        if (boardEntity != null) {
            // todo 예외처리
        }
        User userEntity=userRepository.findByUsername(inviteRequestDto.getUserName()).orElse(null);
        if (userEntity != null) {
            // todo 예외처리
        }
        BoardWorkspace boardWorkspace = BoardWorkspace.builder()
                .board(boardEntity)
                .user(userEntity)
                .status(InvitationEnum.PENDING)
                .build();
        BoardWorkspace workspaceEntity = boardWorkspaceRepository.save(boardWorkspace);

        return new InviteResponseDto(workspaceEntity.getUser().getUsername());
    }

    public List<InvitationListDto> getInvitationList(Long userId) {
        return boardWorkspaceRepository.findAllBoardWorkspacesByUserId(userId);
    }

    @Transactional
    public void acceptInvitation(Long workspaceId) {
        BoardWorkspace boardWorkspace = boardWorkspaceRepository.findById(workspaceId).orElse(null);
        if (boardWorkspace != null) {
            boardWorkspace.editStatus(InvitationEnum.ACCEPTED);
            boardWorkspaceRepository.save(boardWorkspace);
        }else{
            // todo 초대받은 기록이 없음
        }
    }
    @Transactional
    public void rejectInvitation(Long workspaceId) {
        BoardWorkspace boardWorkspace = boardWorkspaceRepository.findById(workspaceId).orElse(null);
        if (boardWorkspace != null) {
            boardWorkspace.editStatus(InvitationEnum.DECLINED);
            boardWorkspaceRepository.save(boardWorkspace);
        }else{
            // todo 초대받은 기록이 없음
        }
    }

    public List<MemberDto> getBoardMember(Long boardId) {
        List<MemberDto> userNameList= boardWorkspaceRepository.findUsernamesByBoardId(boardId);
        return userNameList;
    }
}
