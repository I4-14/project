package com.sparta.trello.columns.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.trello.columns.entity.Columns;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.sparta.trello.columns.entity.QColumns.columns;

@Repository
@RequiredArgsConstructor
public class ColumnsCustomRepositoryImpl implements ColumnsCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    @Override
    public Optional<Long> findMaxOrderNum() {
        Long maxOrderNum = jpaQueryFactory.select(columns.orderNum.max())
                .from(columns)
                .fetchOne();
        return Optional.ofNullable(maxOrderNum);
    }

    @Override
    public Optional<Long> findOrderNumById(Long id) {
        Long result = jpaQueryFactory.select(columns.orderNum)
                .from(columns)
                .where(columns.id.eq(id))
                .fetch().get(0);
        return Optional.ofNullable(result);
    }

    @Override
    public List<Columns> findByBetweenColumnIdAndInFrontOfId(Long smallerOrderNum, Long biggerOrderNum) {
        List<Columns> result = jpaQueryFactory.selectFrom(columns)
                .where(columns.orderNum.between(smallerOrderNum, biggerOrderNum))
                .fetch();
        return result;
    }
}
