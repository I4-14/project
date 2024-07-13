//package com.sparta.trello.auth.controller;
//
//import com.sparta.trello.auth.dto.LoginRequestDto;
//import com.sparta.trello.auth.dto.LoginResponseDto;
//import com.sparta.trello.auth.dto.SignupRequestDto;
//import com.sparta.trello.auth.dto.SignupResponseDto;
//import com.sparta.trello.auth.security.UserDetailsImpl;
//import com.sparta.trello.auth.service.AuthService;
//import com.sparta.trello.common.ApiResponse;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    // 회원가입 API
//    @PostMapping("/signup")
//    public ResponseEntity<ApiResponse> signup(@Valid @RequestBody SignupRequestDto requestDto) {
//        SignupResponseDto responseDto = authService.signup(requestDto);
//        ApiResponse response = ApiResponse.builder()
//                .msg("회원가입 성공")
//                .statuscode(String.valueOf(HttpStatus.CREATED.value()))
//                .data(responseDto)
//                .build();
//        return ResponseEntity.status(HttpStatus.CREATED).body(response);
//    }
//
//    // 로그인 API
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequestDto requestDto) {
//        LoginResponseDto responseDto = authService.login(requestDto);
//        ApiResponse response = ApiResponse.builder()
//                .msg("로그인 성공")
//                .statuscode(String.valueOf(HttpStatus.OK.value()))
//                .data(responseDto)
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    // 로그아웃 API
//    @PostMapping("/logout")
//    public ResponseEntity<ApiResponse> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        authService.logout(userDetails.getUser());
//        ApiResponse response = ApiResponse.builder()
//                .msg("로그아웃 성공")
//                .statuscode(String.valueOf(HttpStatus.OK.value()))
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    // 회원탈퇴 API
//    @PostMapping("/withdraw")
//    public ResponseEntity<ApiResponse> withdraw(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        authService.withdraw(userDetails.getUser());
//        ApiResponse response = ApiResponse.builder()
//                .msg("회원탈퇴 성공")
//                .statuscode(String.valueOf(HttpStatus.NO_CONTENT.value()))
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//}
