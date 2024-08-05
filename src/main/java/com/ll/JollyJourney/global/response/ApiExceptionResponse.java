package com.ll.JollyJourney.global.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class ApiExceptionResponse {
    private int code;
    private String message;

    public ApiExceptionResponse(int code, String message){
        this.code = code;
        this.message = message;
    }
}