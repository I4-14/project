package com.sparta.trello.board.service;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    private int PAGE_SIZE = 5;

    public List<BoardResponseDto> getBoardList(int page, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        Page<BoardResponseDto> boards = boardRepository.findAll(pageable).map(BoardResponseDto::new);
        List<BoardResponseDto> boardList = boards.getContent();
        return boardList;
    }

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        // 유저 role이 manager인지 확인
        if (user.getRole().equals(Role.MANAGER)){
            Board boardEntity = new Board(boardRequestDto);
            Board savedBoard = boardRepository.save(boardEntity);
            return new BoardResponseDto(savedBoard);
        }else{
            // todo 예외 처리 manager만 보드를 생성할 수 있습니다.
            return null;
        }
    }


}
