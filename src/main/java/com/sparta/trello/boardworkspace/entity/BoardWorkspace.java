package com.sparta.trello.boardworkspace.entity;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "boardWorkspace")
@Getter
@NoArgsConstructor
public class BoardWorkspace extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public BoardWorkspace(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
