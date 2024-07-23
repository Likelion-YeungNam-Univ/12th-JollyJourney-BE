package com.ll.JollyJourney.global.security.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (request.getRequestURI().equals("/members/login/error")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "UNAUTHORIZED");
        } else {
            response.sendRedirect("/members/login/error");
        }
    }
}
