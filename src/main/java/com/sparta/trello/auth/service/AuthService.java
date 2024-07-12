package com.sparta.trello.auth.service;

import com.sparta.trello.auth.dto.LoginRequestDto;
import com.sparta.trello.auth.dto.LoginResponseDto;
import com.sparta.trello.auth.dto.SignupRequestDto;
import com.sparta.trello.auth.dto.SignupResponseDto;
import com.sparta.trello.auth.entity.Role;
import com.sparta.trello.auth.entity.User;
import com.sparta.trello.auth.entity.UserStatus;
import com.sparta.trello.auth.repository.UserRepository;
import com.sparta.trello.jwt.JwtUtil;
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

    // 회원가입
    public SignupResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        Optional<User> checkUser = userRepository.findByUsername(username);
        if (checkUser.isPresent()) {
            throw new IllegalArgumentException("이미 중복된 사용자가 존재합니다.");
        }

        // 사용자 권한 확인
        Role role = Role.USER;
        if (requestDto.isManager()) {
            if (!MANAGER_TOKEN.equals(requestDto.getManagerToken())) {
                throw new IllegalArgumentException("잘못된 암호입니다.");
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

    // 로그인
    @Transactional
    public LoginResponseDto login(LoginRequestDto requestDto) {
        User user = userRepository.findByUsername(requestDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 다시 확인해주세요.")
        );

        if (!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        if (!user.isExist()) {
            throw new IllegalArgumentException("탈퇴한 사용자입니다.");
        }

        String accessToken = jwtUtil.createAccessToken(requestDto.getUsername(), user.getRole());
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), user.getRole());

        user.updateRefresh(refreshToken);
        userRepository.save(user);

        return new LoginResponseDto(accessToken, refreshToken);
    }

    // 로그아웃
    @Transactional
    public void logout(User user) {
        User finduser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 다시 확인해주세요.")
        );

        finduser.updateRefresh("");
        userRepository.save(finduser);
    }

    // 회원탈퇴
    @Transactional
    public void withdraw(User user) {
        User finduser = userRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("아이디를 다시 확인해주세요.")
        );

        finduser.updateRefresh("");
        finduser.updateStatus(UserStatus.LEAVE);

        userRepository.save(finduser);
    }
}
