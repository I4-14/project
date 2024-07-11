package com.sparta.trello.columns.controller;

import com.sparta.trello.columns.dto.ColumnsRequestDto;
import com.sparta.trello.columns.dto.ColumnsResponseDto;
import com.sparta.trello.columns.services.ColumnsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnsController {
    private final ColumnsServices columnsServices;

    @PostMapping("/boards/{id}/columns")
    public ColumnsResponseDto createColumns(@PathVariable("id") Long id, @RequestBody ColumnsRequestDto requestDto) {
        return columnsServices.createColumns(id, requestDto);
    }
    @PutMapping("/columns/{id}")
    public ColumnsResponseDto updateColumns(@PathVariable("id") Long id, @RequestBody ColumnsRequestDto requestDto) {
        return columnsServices.updateColumns(id, requestDto);
    }
    @DeleteMapping("/columns/{id}")
    public ColumnsResponseDto deleteColumns(@PathVariable("id") Long id) {
        return columnsServices.deleteColumns(id);
    }
}
