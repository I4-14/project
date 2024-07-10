package com.sparta.trello.board.entity;

import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;

@Entity
@Table(name = "boards")
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

}
