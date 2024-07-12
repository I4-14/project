package com.sparta.trello.columns.entity;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.card.entity.Card;
import com.sparta.trello.columns.dto.ColumnsRequestDto;
import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "ordernum")
    private Long orderNum;

    public void updateComment(ColumnsRequestDto requestDto) {
        this.category = CategoryEnum.valueOf(requestDto.getCategory());
    }
    public void updateOrderNum(Long orderNum) {
        this.orderNum = orderNum;
    }
    public void subtractOrderNum() {
        this.orderNum--;
    }
    public void addOrderNum() {
        this.orderNum++;
    }

    @OneToMany(mappedBy = "columns", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Card> cards = new ArrayList<>();

}

