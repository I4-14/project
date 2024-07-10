package com.sparta.trello.columns.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "column")
public class Columns {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;
}
