package com.sparta.trello.board.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "board")
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;
    @Column(length = 200, nullable = false)
    private String description;
}
