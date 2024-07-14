//package com.sparta.trello.auth.controller;
//
//import com.sparta.trello.auth.dto.LoginRequestDto;
//import com.sparta.trello.auth.dto.SignupRequestDto;
//import com.sparta.trello.auth.dto.SignupResponseDto;
//import com.sparta.trello.auth.dto.TokenResponseDto;
//import com.sparta.trello.auth.security.UserDetailsImpl;
//import com.sparta.trello.auth.service.AuthService;
//import com.sparta.trello.common.ApiResponse;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    /**
//     * 회원가입 API
//     *
//     * @param requestDto 회원가입 요청 데이터
//     * @return 회원가입 응답 데이터
//     */
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
//    /**
//     * 로그인 API
//     *
//     * @param requestDto 로그인 요청 데이터
//     * @return 발급된 토큰 응답 데이터
//     */
//    @PostMapping("/login")
//    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequestDto requestDto) {
//        TokenResponseDto responseDto = authService.login(requestDto);
//        ApiResponse response = ApiResponse.builder()
//                .msg("로그인 성공")
//                .statuscode(String.valueOf(HttpStatus.OK.value()))
//                .data(responseDto)
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//    /**
//     * 로그아웃 API
//     *
//     * @param userDetails 인증된 사용자 정보
//     * @return 로그아웃 성공 응답
//     */
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
//    /**
//     * 회원탈퇴 API
//     *
//     * @param userDetails 인증된 사용자 정보
//     * @return 회원탈퇴 성공 응답
//     */
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
//    /**
//     * 토큰 재발급 API
//     *
//     * @param request HTTP 요청 정보
//     * @return 재발급된 토큰 응답 데이터
//     */
//    @PostMapping("/refresh-token")
//    public ResponseEntity<ApiResponse> refreshToken(HttpServletRequest request) {
//        TokenResponseDto responseDto = authService.refreshToken(request);
//        ApiResponse response = ApiResponse.builder()
//                .msg("토큰 재발급 성공")
//                .statuscode(String.valueOf(HttpStatus.OK.value()))
//                .data(responseDto)
//                .build();
//        return ResponseEntity.status(HttpStatus.OK).body(response);
//    }
//
//}
