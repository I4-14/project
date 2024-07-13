package com.sparta.trello.auth.service;

import com.sparta.trello.auth.dto.LoginRequestDto;
import com.sparta.trello.auth.dto.SignupRequestDto;
import com.sparta.trello.auth.dto.SignupResponseDto;
import com.sparta.trello.auth.dto.TokenResponseDto;
import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.entity.UserStatus;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.common.exception.CustomException;
import com.sparta.trello.common.exception.ErrorEnum;
import com.sparta.trello.common.jwt.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${manager.token.key}")
    private String MANAGER_TOKEN;

    /**
     * 회원가입
     *
     * @param requestDto 회원가입 요청 데이터
     * @return 회원가입 응답 데이터
     */
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUser = userRepository.findByUsername(username);
        // 중복 사용자 확인
        if (checkUser.isPresent()) {
            throw new CustomException(ErrorEnum.DUPLICATE_USER);
        }

        // 사용자 권한 확인
        Role role = Role.USER;
        if (requestDto.isManager()) {
            if (!MANAGER_TOKEN.equals(requestDto.getManagerToken())) {
                throw new CustomException(ErrorEnum.BAD_MANAGER_TOKEN);
            }
            role = Role.MANAGER;
        }

        User user = User.builder()
                .username(username)
                .password(password)
                .name(requestDto.getName())
                .userStatus(UserStatus.NORMAL)
                .role(role)
                .refreshToken("")
                .build();

        userRepository.save(user);
        return new SignupResponseDto(user);
    }

    /**
     * 로그인
     *
     * @param requestDto 로그인 요청 데이터
     * @return 발급된 토큰 응답 데이터
     */
    @Transactional
    public TokenResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new CustomException(ErrorEnum.USER_NOT_FOUND)
        );

        // 회원 상태 확인
        if (!user.isExist()) {
            throw new CustomException(ErrorEnum.WITHDRAW_USER);
        }

        // 암호화 비밀번호 검증
        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new CustomException(ErrorEnum.INCORRECT_PASSWORD);
        }

        String accessToken = jwtUtil.createAccessToken(requestDto.getUsername(), user.getRole());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());

        user.updateRefresh(refreshToken);

        return new TokenResponseDto(accessToken, refreshToken);
    }

    /**
     * 로그아웃
     *
     * @param user 유저 정보
     */
    @Transactional
    public void logout(User user) {
        User finduser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorEnum.USER_NOT_FOUND)
        );

        // 회원 상태 확인
        if (!user.isExist()) {
            throw new CustomException(ErrorEnum.WITHDRAW_USER);
        }

        // DB refresh 토큰 삭제
        finduser.updateRefresh("");
    }

    /**
     * 회원탈퇴
     *
     * @param user 유저 정보
     */
    @Transactional
    public void withdraw(User user) {
        User finduser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new CustomException(ErrorEnum.USER_NOT_FOUND)
        );

        // 회원 상태 확인
        if (!user.isExist()) {
            throw new CustomException(ErrorEnum.WITHDRAW_USER);
        }

        finduser.updateRefresh("");
        finduser.updateStatus(UserStatus.LEAVE);
    }
}
