package com.sparta.trello.board.dto;

import com.sparta.trello.board.entity.Board;

public class BoardResponseDto {
    private String title;
    private String description;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
    }
}
