package com.sparta.trello.board.repository;

import com.sparta.trello.board.entity.Board;
import org.springframework.data.domain.Page;

public interface BoardRepositoryCustom {
    Page<Board> getBoardList(int page, String sortBy) ;
}
