package com.sparta.trello.board.dto;

import com.sparta.trello.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private String title;
    private String description;
    private Long id;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.id = board.getId();
    }
}
