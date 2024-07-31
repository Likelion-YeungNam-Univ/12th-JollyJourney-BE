package com.ll.JollyJourney.domain.auth.dto;

public record TokenRes(
        String accessToken,

        String refreshToken
) {
}
