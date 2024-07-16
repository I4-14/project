package com.sparta.trello.board.dto;

import com.sparta.trello.board.entity.Board;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    private String title;
    private String description;
    private Long id;
    private boolean isMember;

    public BoardResponseDto(Board board,boolean isMember) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.id = board.getId();
        this.isMember = isMember;
    }
    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.description = board.getDescription();
        this.id = board.getId();
        this.isMember = true;
    }
}
