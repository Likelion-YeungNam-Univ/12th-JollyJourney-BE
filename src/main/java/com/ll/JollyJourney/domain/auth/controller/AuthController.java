package com.ll.JollyJourney.domain.auth.controller;

import com.ll.JollyJourney.domain.auth.dto.SignInReq;
import com.ll.JollyJourney.domain.auth.dto.SignUpReq;
import com.ll.JollyJourney.domain.auth.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReq request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpReq request) {
        return ResponseEntity.ok(authService.signUp(request));
    }
}
