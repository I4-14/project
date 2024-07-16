package com.sparta.trello.boardworkspace.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import com.sparta.trello.boardworkspace.entity.BoardWorkspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.trello.boardworkspace.entity.QBoardWorkspace.boardWorkspace;

@Repository
@RequiredArgsConstructor
public class BoardWorkspaceRepositoryImpl implements BoardWorkspaceRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<MemberDto> findUsernamesByBoardId(Long boardId) {

        return jpaQueryFactory
                .select(Projections.constructor(MemberDto.class,
                        boardWorkspace.user.id,
                        boardWorkspace.user.username))
                .from(boardWorkspace)
                .where(boardWorkspace.board.id.eq(boardId))
                .fetch();
    }

    @Override
    public boolean existsByBoardAndUser(Board board, User user) {

        long count = jpaQueryFactory
                .selectFrom(boardWorkspace)
                .where(
                        boardWorkspace.board.eq(board)
                                .and(boardWorkspace.user.eq(user))
                )
                .fetchCount();

        return count > 0;
    }

    @Override
    public BoardWorkspace findByBoardIdAndUserId(Long boardId, Long userId) {
        return jpaQueryFactory.selectFrom(boardWorkspace)
                .where(
                        boardWorkspace.board.id.eq(boardId)
                                .and(boardWorkspace.user.id.eq(userId))
                )
                .fetchOne();

    }
}
