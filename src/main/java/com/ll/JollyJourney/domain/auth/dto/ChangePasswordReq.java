package com.ll.JollyJourney.domain.auth.dto;

public record ChangePasswordReq(
        String email,
        String oldPassword,
        String newPassword
) {
}
