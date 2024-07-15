package com.sparta.trello.boardworkspace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvitationListDto {
    private Long id;
    private String title;
    private String description;
}
