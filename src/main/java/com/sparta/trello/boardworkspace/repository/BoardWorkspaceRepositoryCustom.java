package com.sparta.trello.boardworkspace.repository;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import com.sparta.trello.boardworkspace.entity.BoardWorkspace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardWorkspaceRepositoryCustom {
    List<MemberDto> findUsernamesByBoardId(Long boardId);
    boolean existsByBoardAndUser(Board board, User user);

    BoardWorkspace findByBoardIdAndUserId(Long boardId, Long userId);
}
