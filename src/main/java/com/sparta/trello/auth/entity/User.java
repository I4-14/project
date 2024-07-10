package com.sparta.trello.auth.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 40)
    private String username;  // 사용자 ID

    @Column(nullable = false, length = 60)
    private String password;  // 사용자 Password

    @Column(nullable = false, length = 20)
    private String name;  // 사용자 이름

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // 문자열로 저장
    private Role role; // 권한

    @Column(name = "user_status", nullable = false)
    @Enumerated(EnumType.STRING) // 문자열로 저장
    private UserStatus userStatus; // 사용자 상태

    @Column(name = "refresh_token",nullable = false)
    private String refreshToken; // 리프레시 토큰
}
