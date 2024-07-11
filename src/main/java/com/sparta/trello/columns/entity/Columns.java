package com.sparta.trello.columns.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.columns.dto.ColumnsRequestDto;
import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "columns")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Columns extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Enumerated(value = EnumType.STRING)
    private CategoryEnum category;

    public void updateComment(ColumnsRequestDto requestDto) {
        this.category = CategoryEnum.valueOf(requestDto.getCategory());
    }
}

