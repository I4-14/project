package com.sparta.trello.auth.dto;

import com.sparta.trello.auth.entity.User;
import lombok.Getter;

@Getter
public class SignupResponseDto {
    private String username;
    private String name;

    public SignupResponseDto(User user) {
        this.username = user.getUsername();
        this.name = user.getName();
    }
}
