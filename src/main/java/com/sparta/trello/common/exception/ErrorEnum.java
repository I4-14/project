package com.sparta.trello.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum ErrorEnum {

    // 필요한 에러들 만드시고 사용하시면 됩니다.

    // Token
    INVALID_TOKEN(UNAUTHORIZED, "유효하지 않은 JWT 서명입니다."),
    TOKEN_EXPIRATION(UNAUTHORIZED, "만료된 토큰입니다. 재로그인 해주세요."),
    NOT_SUPPORTED_TOKEN(UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다."),
    FALSE_TOKEN(BAD_REQUEST, "잘못된 JWT 토큰입니다."),
    HEADER_NOT_FOUND_AUTH(BAD_REQUEST, "권한 헤더가 잘못되었거나 누락되었습니다."),
    TOKEN_VALIDATE(BAD_REQUEST, "Invalid JWT token."),
    INVALID_REFRESH_TOKEN(UNAUTHORIZED, "유효하지 않은 JWT 토큰입니다."),
    UNMATCHED_TOKEN(BAD_REQUEST, "일치하지 않는 토큰입니다."),

    // User
    INVALID_USERNAME(BAD_REQUEST,"아이디는 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)로 구성되어야 합니다."),
    INVALID_PASSWORD(BAD_REQUEST,"최소 8자 이상, 15자 이하이며 알파벳 대소문자(az, AZ), 숫자(0~9),특수문자로 구성되어야 합니다."),
    INCORRECT_PASSWORD(BAD_REQUEST,"비밀번호가 일치하지 않습니다."),
    USER_NOT_FOUND(BAD_REQUEST, "등록되지 않은 사용자입니다."),
    INCORRECT_USER(BAD_REQUEST,"사용자가 동일하지 않습니다."),
    DUPLICATE_USER(BAD_REQUEST,"이미 등록된 사용자 입니다."),
    WITHDRAW_USER(BAD_REQUEST, "탈퇴한 회원입니다."),
    BANNED_USER(FORBIDDEN, "BAN 처리된 사용자입니다."),
    BAD_MANAGER_TOKEN(BAD_REQUEST, "잘못된 암호입니다."),
    HEADER_NOT_FOUND_REFRESH(BAD_REQUEST,"헤더에 토큰이 존재하지 않습니다."),

    // Post
    POST_NOT_FOUND(BAD_REQUEST, "등록되지 않은 게시글입니다."),
    NON_EXISTENT_ELEMENT(BAD_REQUEST,"존재하지 않는 요소입니다."),


    COMMENT_NOT_FOUND(BAD_REQUEST, "등록되지 않은 댓글입니다."),

    USER_NOT_AUTHENTICATED(UNAUTHORIZED, "인증되지 않은 사용자입니다. 로그인해주세요."),

    // Board
    BOARD_NOT_FOUND(NOT_FOUND, "존재하지 않는 보드입니다."),

    // Column
    COLUMN_NOT_FOUND(NOT_FOUND, "존재하지 않는 컬럼입니다."),
    SAME_COLUMN(BAD_REQUEST, "같은 컬럼을 선택했습니다."),
    DUPLICATE_CATEGORY(BAD_REQUEST, "중복된 컬럼 이름이 존재합니다."),

    // Card
    CARD_NOT_FOUND(NOT_FOUND, "존재하지 않는 카드입니다."),
    WRONG_POSITION_NUMBER(BAD_REQUEST, "잘못된 위치번호 입니다."),


    // Comment
    COMMENT_NOT_FOUND(BAD_REQUEST, "등록되지 않은 댓글입니다.");


    HttpStatus httpStatus;
    String msg;
}
