package com.sparta.trello.board.service;

import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.board.dto.BoardRequestDto;
import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.repository.BoardRepository;
import com.sparta.trello.boardworkspace.repository.BoardWorkspaceRepository;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import jakarta.transaction.Transactional;
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
    private final UserRepository userRepository;
    private final BoardWorkspaceRepository boardWorkspaceRepository;

    private int PAGE_SIZE = 5;

    public List<BoardResponseDto> getBoardList(int page, String sortBy) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        Page<BoardResponseDto> boards = boardRepository.findAll(pageable).map(BoardResponseDto::new);
        List<BoardResponseDto> boardList = boards.getContent();
        return boardList;
    }

    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board boardEntity = new Board(boardRequestDto);
        Board savedBoard = boardRepository.save(boardEntity);
        return new BoardResponseDto(savedBoard);

    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Board boardEntity = boardRepository.findById(boardId).orElse(null);
        if (boardEntity != null) {
            boardEntity.update(boardRequestDto);
            boardRepository.save(boardEntity);
            return new BoardResponseDto(boardEntity);
        } else {
            // todo board가 없는 경우 예외처리
            return null;
        }
    }


    /**
     * 보드 삭제
     * @param boardId 보드 id
     * @param temUser 유저
     */
    public void deleteBoard(Long boardId, User temUser) {
        Board boardEntity = boardRepository.findById(boardId).orElse(null);
        if (boardEntity != null) {
            boardRepository.delete(boardEntity);
            System.out.println(true);

        }else{
            throw new CustomException(ErrorEnum.NON_EXISTENT_ELEMENT);
        }

    }


}
