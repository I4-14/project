package com.sparta.trello.columns.repository;

import com.sparta.trello.columns.entity.Columns;

import java.util.List;
import java.util.Optional;

public interface ColumnsCustomRepository {
    Optional<Long> findMaxOrderNum();
    Optional<Long> findOrderNumById(Long id);

    List<Columns> findByBetweenColumnIdAndInFrontOfId(Long orginalOrderNum, Long destinationOrderNum);
}
