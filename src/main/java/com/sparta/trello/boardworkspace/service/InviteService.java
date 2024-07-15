package com.sparta.trello.boardworkspace.service;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.boardworkspace.dto.InviteRequestDto;
import com.sparta.trello.boardworkspace.dto.InviteResponseDto;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import com.sparta.trello.boardworkspace.entity.BoardWorkspace;
import com.sparta.trello.boardworkspace.repository.BoardWorkspaceRepository;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InviteService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final BoardWorkspaceRepository boardWorkspaceRepository;


    @Transactional
    public InviteResponseDto inviteBoard(Long boardId, InviteRequestDto inviteRequestDto) {
        Optional<Board> boardEntity = boardRepository.findById(boardId);
        if (!boardEntity.isPresent()) {
            throw new CustomException(ErrorEnum.NON_EXISTENT_ELEMENT);
        }
        Optional<User> userEntity=userRepository.findByUsername(inviteRequestDto.getUserName());
        if (!userEntity.isPresent()) {
            throw new CustomException(ErrorEnum.NON_EXISTENT_ELEMENT);
        }
        if(userEntity.get().getRole().equals(Role.USER)){
            BoardWorkspace boardWorkspace = BoardWorkspace.builder()
                    .board(boardEntity.get())
                    .user(userEntity.get())
                    .build();
            BoardWorkspace workspaceEntity = boardWorkspaceRepository.save(boardWorkspace);
            return new InviteResponseDto(workspaceEntity.getUser().getUsername());
        }else {
            throw new CustomException(ErrorEnum.MANAGER_CANNOT_BE_INVITED);
        }


    }


    public List<MemberDto> getBoardMember(Long boardId) {
        List<MemberDto> userNameList= boardWorkspaceRepository.findUsernamesByBoardId(boardId);
        return userNameList;
    }
    @Transactional
    public void deleteMember(Long boardId, Long memberId) {
        BoardWorkspace boardWorkspace = boardWorkspaceRepository.findByBoardIdAndMemberId(boardId,memberId);
        boardWorkspaceRepository.delete(boardWorkspace);

    }
}
