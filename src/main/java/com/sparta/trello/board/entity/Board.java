package com.sparta.trello.board.entity;

import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boards")
@Builder
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.description = boardRequestDto.getDescription();

    }

    public void update(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.description = boardRequestDto.getDescription();
    }
}
