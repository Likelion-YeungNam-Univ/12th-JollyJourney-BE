package com.ll.JollyJourney.global.exception;

import com.ll.JollyJourney.global.response.ApiExceptionResponse;
import com.ll.JollyJourney.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    protected ApiResponse<ApiExceptionResponse> handleException(Exception ex) {
        log.error("예외 발생: " + ex.toString());

        final ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                500,
                ex.toString()
        );
        return ApiResponse.createError(apiExceptionResponse);
    }
}
