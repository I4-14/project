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
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
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

        Optional<User> checkUser = Optional.ofNullable(findByUsername(username));
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
        User user = findByUsername(requestDto.getUsername());

        // 회원 상태 확인
        checkUserStatus(user);

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
        User finduser = findByUsername(user.getUsername());

        // 회원 상태 확인
        checkUserStatus(finduser);

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
        User finduser = findByUsername(user.getUsername());

        // 회원 상태 확인
        checkUserStatus(finduser);

        finduser.updateRefresh("");
        finduser.updateStatus(UserStatus.LEAVE);
    }

    /**
     * Access Token 이 만료되었을 때, Refresh Token 을 사용하여 새로운 토큰 발급
     *
     * @param request HTTP 요청 정보
     * @return 재발급된 토큰 응답 데이터
     */
    @Transactional
    public TokenResponseDto refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtil.getRefreshJwtFromHeader(request);

        // 토큰 유효성 및 만료 확인
        if (!jwtUtil.validateToken(refreshToken) || jwtUtil.isTokenExpired(refreshToken)) {
            throw new CustomException(ErrorEnum.INVALID_REFRESH_TOKEN);
        }

        Claims claims = jwtUtil.getUserInfoFromToken(refreshToken);
        String username = claims.getSubject();
        Role role = jwtUtil.getRoleFromToken(refreshToken);

        User user = findByUsername(username);

        // DB에 저장된 리프레시 토큰 검증
        String userRefreshToken = user.getRefreshToken().replace("Bearer ", "");
        if (!refreshToken.equals(userRefreshToken)) {
            throw new CustomException(ErrorEnum.UNMATCHED_TOKEN);
        }

        String newAccessToken = jwtUtil.createAccessToken(username, role);
        String newRefreshToken = jwtUtil.createRefreshToken(username, role);

        user.updateRefresh(newRefreshToken);

        return new TokenResponseDto(newAccessToken, newRefreshToken);
    }

    /**
     * username 을 통해 유저 정보 가져오기
     *
     * @param username 사용자 ID
     * @return 유저 정보
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(ErrorEnum.USER_NOT_FOUND)
        );
    }

    /**
     * 회원 가입상태 검증 (정상, 탈퇴)
     *
     * @param user 요청한 유저 정보
     */
    public void checkUserStatus(User user) {
        if (!user.isExist()) {
            throw new CustomException(ErrorEnum.WITHDRAW_USER);
        }
    }
}
