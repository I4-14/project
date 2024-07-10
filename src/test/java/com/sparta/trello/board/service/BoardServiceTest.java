package com.sparta.trello.board.service;

import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import org.junit.jupiter.api.Test;


public class BoardServiceTest {
    private final BoardRepository boardRepository;

    public BoardServiceTest(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }
    @Test
    public void createBoardTest() {
        Board board = Board.builder()
                .title("보드1")
                .description("보드 설명")
                .build();
        boardRepository.save(board);
    }
}
