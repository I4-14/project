package com.sparta.trello.columns.repository;

import com.sparta.trello.columns.entity.Columns;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnsRepository extends JpaRepository<Columns, Long>, ColumnsCustomRepository{
}
