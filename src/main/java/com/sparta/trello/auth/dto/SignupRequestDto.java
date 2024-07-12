package com.sparta.trello.auth.dto;

import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupRequestDto {
    @NotBlank(message = "사용자 ID는 필수 입력 사항입니다.")
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "사용자 ID는 알파벳 소문자와 숫자로 이루어진 4자에서 10자 사이여야 합니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String password;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    private Role role;

}
