package com.sparta.trello.boardworkspace.repository;

import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardWorkspaceRepositoryCustom {
    List<InvitationListDto> findAllBoardWorkspacesByUserId(Long userId);

}
