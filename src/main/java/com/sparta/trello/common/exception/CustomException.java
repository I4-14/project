package com.sparta.trello.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 사용법
 * throw new CustomException(ErrorEnum.NON_EXISTENT_ELEMENT);
 */
@Getter
@RequiredArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorEnum statusEnum;
}