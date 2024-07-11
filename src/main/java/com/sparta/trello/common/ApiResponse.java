package com.sparta.trello.common;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiResponse<T> {
    private String msg;
    private String statuscode;
    private T data;

    public ApiResponse(String msg, String statuscode, T data) {
        this.msg = msg;
        this.statuscode = statuscode;
        this.data = data;
    }

}
