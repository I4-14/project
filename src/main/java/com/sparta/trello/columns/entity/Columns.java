package com.sparta.trello.columns.entity;

import com.sparta.trello.board.entity.Board;
import jakarta.persistence.*;

@Entity
@Table(name = "column")
public class Columns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board boardId;

    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;
}
