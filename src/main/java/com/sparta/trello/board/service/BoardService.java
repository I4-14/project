package com.sparta.trello.board.service;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardWorkspaceRepository boardWorkspaceRepository;

    /**
     * 보드 리스트 불러오기
     * @param page  페이지
     * @param sortBy 기준
     * @return
     */
    public List<BoardResponseDto> getBoardList(int page, String sortBy, User user) {
        Page<Board> boards = boardRepository.getBoardList(page, sortBy);
        List<BoardResponseDto> boardList;

        if(user.getRole().equals(Role.MANAGER)) {
            boardList = boards.getContent().stream().map(board -> {
                boolean isMember = true;
                return new BoardResponseDto(board, isMember);
            }).collect(Collectors.toList());
        }else{
            boardList = boards.getContent().stream().map(board -> {
                boolean isMember = boardWorkspaceRepository.existsByBoardAndUser(board, user);
                return new BoardResponseDto(board, isMember);
            }).collect(Collectors.toList());
        }

        return boardList;
    }

    /**
     * 보드 생성
     * @param boardRequestDto
     * @return
     */
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board boardEntity = new Board(boardRequestDto);
        Board savedBoard = boardRepository.save(boardEntity);
        return new BoardResponseDto(savedBoard);

    }

    /**
     * 보드 업데이트
     * @param boardId
     * @param boardRequestDto
     * @return
     */
    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Optional<Board> boardEntity = boardRepository.findById(boardId);
        if (boardEntity.isPresent()) {
            boardEntity.get().update(boardRequestDto);
            boardRepository.save(boardEntity.get());
            return new BoardResponseDto(boardEntity.get());
        } else {
            throw new CustomException(ErrorEnum.NON_EXISTENT_ELEMENT);
        }
    }


    /**
     * 보드 삭제
     * @param boardId 보드 id
     */
    @Transactional
    public void deleteBoard(Long boardId) {
        Optional<Board> boardEntity = boardRepository.findById(boardId);
        if (boardEntity.isPresent()) {
            boardRepository.delete(boardEntity.get());
        }else{
            throw new CustomException(ErrorEnum.NON_EXISTENT_ELEMENT);
        }

    }


}
