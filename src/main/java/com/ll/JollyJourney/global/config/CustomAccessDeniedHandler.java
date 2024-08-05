package com.ll.JollyJourney.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        String accept = request.getHeader("Accept");
        String requestURI = request.getRequestURI();

        if ("application/json".equals(accept)) {
            ErrorResponse error = ErrorResponse.builder()
                    .code("403")
                    .message("접근 권한이 없습니다.")
                    .build();

            String result = mapper.writeValueAsString(error);

            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(result);
        } else {
            String redirectUrl;
            if (requestURI.contains("/journal/detail/") && requestURI.contains("/comments")) {
                redirectUrl = requestURI.substring(0, requestURI.indexOf("/comments"));    // 댓글 관련 요청
            } else {
                redirectUrl = "/journal/list";
            }
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write("<script>alert('권한이 없습니다!'); window.location='" + redirectUrl + "';</script>");
        }
    }

    public static class ErrorResponse {
        private String code;
        private String message;

        public static ErrorResponseBuilder builder() {
            return new ErrorResponseBuilder();
        }

        public static class ErrorResponseBuilder {
            private String code;
            private String message;

            ErrorResponseBuilder() {}

            public ErrorResponseBuilder code(String code) {
                this.code = code;
                return this;
            }

            public ErrorResponseBuilder message(String message) {
                this.message = message;
                return this;
            }

            public ErrorResponse build() {
                ErrorResponse errorResponse = new ErrorResponse();
                errorResponse.code = this.code;
                errorResponse.message = this.message;
                return errorResponse;
            }
        }
    }
}