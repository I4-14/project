package com.sparta.trello.auth.controller;

import com.sparta.trello.auth.dto.LoginRequestDto;
import com.sparta.trello.auth.dto.LoginResponseDto;
import com.sparta.trello.auth.dto.SignupRequestDto;
import com.sparta.trello.auth.dto.SignupResponseDto;
import com.sparta.trello.auth.security.UserDetailsImpl;
import com.sparta.trello.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    // 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@Valid @RequestBody SignupRequestDto requestDto) {
        SignupResponseDto responseDto = authService.signup(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = authService.login(requestDto);
        return ResponseEntity.ok().body(responseDto);
    }

    // 로그아웃 API
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.logout(userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 회원탈퇴 API
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.withdraw(userDetails.getUser());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
