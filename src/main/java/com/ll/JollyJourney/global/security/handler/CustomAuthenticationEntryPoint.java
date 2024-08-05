package com.ll.JollyJourney.global.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.JollyJourney.global.response.ApiExceptionResponse;
import com.ll.JollyJourney.global.response.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        final ApiExceptionResponse apiExceptionResponse = new ApiExceptionResponse(
                403,
                "인증되지 않은 사용자"
        );

        ApiResponse<?> apiResponse = ApiResponse.createError(apiExceptionResponse);
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);

        response.getWriter().write(jsonResponse);

//        Map<String, Object> errorResponse = new HashMap<>();
//        errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
//        errorResponse.put("error", "Unauthorized");
//        errorResponse.put("message", "인증되지 않은 사용자");
//        errorResponse.put("timestamp", LocalDateTime.now());
//
//        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
//        response.getWriter().write(jsonResponse);
    }
}
