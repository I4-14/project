package com.sparta.trello.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 일 경우, 응답 데이터 생략
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
