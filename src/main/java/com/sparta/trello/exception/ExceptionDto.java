package com.sparta.trello.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExceptionDto {
    private String msg;
    private int statuscode;

    public ExceptionDto(ErrorEnum errorEnum) {
        this.msg = errorEnum.getMsg();
        this.statuscode = errorEnum.getHttpStatus().value();
    }

    public ExceptionDto(String msg, int statuscode) {
        this.msg = msg;
        this.statuscode = statuscode;
    }
}
