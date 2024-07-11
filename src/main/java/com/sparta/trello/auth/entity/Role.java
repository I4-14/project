package com.sparta.trello.auth.entity;

public enum Role {
    MANAGER(Authority.MANAGER),  // 매니저 사용자
    USER(Authority.USER);  // 일반 사용자

    private final String authority;

    Role(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String MANAGER = "MANAGER";
        public static final String USER = "USER";
    }
}
