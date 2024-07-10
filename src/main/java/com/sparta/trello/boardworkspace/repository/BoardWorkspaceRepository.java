package com.sparta.trello.boardworkspace.repository;

import com.sparta.trello.boardworkspace.entity.BoardWorkspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardWorkspaceRepository extends JpaRepository<BoardWorkspace, Long> {

}

