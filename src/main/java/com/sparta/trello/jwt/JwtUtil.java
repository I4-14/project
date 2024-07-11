package com.sparta.trello.jwt;

import com.sparta.trello.auth.entity.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Slf4j(topic = "JwtUtil")
@Component
public class JwtUtil {
    // Header KEY 값
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_TOKEN_HEADER = "RefreshToken";
    public static final String BEARER_PREFIX = "Bearer ";

    // 사용자 권한 값의 KEY
    public static final String AUTHORIZATION_KEY = "auth";

    // 토큰 만료시간
    private final long ACCESS_TOKEN_TIME = 60 * 30 * 1000L; // 30분
    private final long REFRESH_TOKEN_TIME = 60 * 60 * 24 * 14 * 1000L; // 14일

    @Value("${jwt.secret.key}")
    private String secretKey;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // Bean 초기화 후 실행되는 메서드
    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    private Key getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS256.getJcaName());
    }

    // Access 토큰 생성
    public String createToken(String username, Role role) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)  // 사용자 식별값
                        .claim(AUTHORIZATION_KEY, role)  // 사용자 권한
                        .setExpiration(new Date(date.getTime() + ACCESS_TOKEN_TIME))  // 만료 시간
                        .setIssuedAt(date)  // 발급일
                        .signWith(key, signatureAlgorithm)  // 암호화 알고리즘
                        .compact();
    }

    // Refresh 토큰 생성
    public String createRefreshToken(String username, Role role) { // 리프레시 토큰 생성
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)  // 사용자 식별값
                        .claim(AUTHORIZATION_KEY, role)  // 사용자 권한
                        .setExpiration(new Date(date.getTime() + REFRESH_TOKEN_TIME))  // 만료 시간
                        .setIssuedAt(date)  // 발급일
                        .signWith(key, signatureAlgorithm)  // 암호화 알고리즘
                        .compact();
    }

    // 토큰 유효성 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            throw new IllegalArgumentException("유효하지 않은 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 JWT 토큰입니다.");
        } catch (ExpiredJwtException e) {
            throw new AccessDeniedException("재로그인 해주세요");
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    // header 에서 JWT 가져오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        return substringToken(bearerToken);
    }

    // JWT 토큰의 Bearer 접두사 제거 후 반환
    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7); // "Bearer " 접두사 제거
        }
        throw new IllegalArgumentException("Not Found Token");
    }

}
