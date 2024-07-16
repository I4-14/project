package com.sparta.trello.auth.entity;

import com.sparta.trello.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Table(name = "user")
@NoArgsConstructor
@DynamicUpdate
public class User extends Timestamped {
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

    @Builder
    public User(String username, String password, String name, Role role, UserStatus userStatus, String refreshToken) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
        this.userStatus = userStatus;
        this.refreshToken = refreshToken;
    }

    public void updateRefresh(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public boolean isExist() {
        return this.userStatus == UserStatus.NORMAL;
    }
}
