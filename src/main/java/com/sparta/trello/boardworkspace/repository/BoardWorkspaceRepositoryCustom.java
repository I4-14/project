package com.sparta.trello.boardworkspace.repository;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardWorkspaceRepositoryCustom {
    List<InvitationListDto> findAllBoardWorkspacesByUserId(Long userId);
    List<MemberDto> findUsernamesByBoardId(Long boardId);
    boolean existsByBoardAndUser(Board board, User user);
}
