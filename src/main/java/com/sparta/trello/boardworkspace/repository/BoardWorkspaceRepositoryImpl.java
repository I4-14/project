package com.sparta.trello.boardworkspace.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.board.entity.Board;
import com.sparta.trello.board.entity.QBoard;
import com.sparta.trello.boardworkspace.dto.InvitationListDto;
import com.sparta.trello.boardworkspace.dto.MemberDto;
import com.sparta.trello.boardworkspace.entity.InvitationEnum;
import com.sparta.trello.boardworkspace.entity.QBoardWorkspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardWorkspaceRepositoryImpl implements BoardWorkspaceRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public List<InvitationListDto> findAllBoardWorkspacesByUserId(Long userId) {
        QBoardWorkspace boardWorkspace = QBoardWorkspace.boardWorkspace;
        QBoard board = QBoard.board;

        return jpaQueryFactory
                .select(Projections.constructor(InvitationListDto.class,
                        boardWorkspace.id,
                        boardWorkspace.board.title,
                        boardWorkspace.board.description,
                        boardWorkspace.status))
                .from(boardWorkspace)
                .join(boardWorkspace.board, board)
                .where(boardWorkspace.user.id.eq(userId))
                .fetch();
    }

    @Override
    public List<MemberDto> findUsernamesByBoardId(Long boardId) {
        QBoardWorkspace qBoardWorkspace = QBoardWorkspace.boardWorkspace;

        return jpaQueryFactory
                .select(Projections.constructor(MemberDto.class,
                        qBoardWorkspace.user.id,
                        qBoardWorkspace.user.username))
                .from(qBoardWorkspace)
                .where(qBoardWorkspace.board.id.eq(boardId)
                        .and(qBoardWorkspace.status.eq(InvitationEnum.ACCEPTED)))
                .fetch();
    }

    @Override
    public boolean existsByBoardAndUser(Board board, User user) {
        QBoardWorkspace qBoardWorkspace = QBoardWorkspace.boardWorkspace;

        long count = jpaQueryFactory
                .selectFrom(qBoardWorkspace)
                .where(
                        qBoardWorkspace.board.eq(board)
                                .and(qBoardWorkspace.user.eq(user))
                                .and(qBoardWorkspace.status.eq(InvitationEnum.ACCEPTED))
                )
                .fetchCount();

        return count > 0;
    }
}
