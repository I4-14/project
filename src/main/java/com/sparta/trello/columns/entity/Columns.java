package com.sparta.trello.columns.entity;

import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;

@Entity
@Table(name = "column")
public class Columns extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;
    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;
}

