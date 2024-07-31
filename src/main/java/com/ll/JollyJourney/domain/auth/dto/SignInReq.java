package com.ll.JollyJourney.domain.auth.dto;

public record SignInReq(
        String email,
        String password
) {
}
