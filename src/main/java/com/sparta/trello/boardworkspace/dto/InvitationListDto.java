package com.sparta.trello.boardworkspace.dto;

import com.sparta.trello.board.dto.BoardResponseDto;
import com.sparta.trello.boardworkspace.entity.InvitationEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvitationListDto {
    private Long id;
    private String title;
    private String description;
    private InvitationEnum status;
}
