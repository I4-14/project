package com.sparta.trello.board.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.board.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.trello.board.entity.QBoard.board;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private final int PAGE_SIZE = 8;

    @Override
    public Page<Board> getBoardList(int page, String sortBy) {

        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        List<Board> boards = queryFactory
                .selectFrom(board)
                .orderBy(getOrderBy(sortBy))
                .offset(pageable.getOffset())
                .limit(PAGE_SIZE)
                .fetch();

        // 전체 개수 조회
        long total = queryFactory
                .selectFrom(board)
                .fetchCount();

        return new PageImpl<>(boards, pageable, total);
    }

    // 정렬 필드를 동적으로 반환하는 메서드
    private OrderSpecifier<?> getOrderBy(String sortBy) {
        switch (sortBy) {
            case "createdAt":
                return board.createdAt.desc();
            case "title":
                return board.title.desc();
            // 다른 정렬 기준 추가
            default:
                return board.createdAt.desc(); // 기본 정렬
        }
    }

}
