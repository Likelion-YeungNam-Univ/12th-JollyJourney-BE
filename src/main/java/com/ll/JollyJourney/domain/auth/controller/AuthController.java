package com.ll.JollyJourney.domain.auth.controller;

import com.ll.JollyJourney.domain.auth.dto.ChangePasswordReq;
import com.ll.JollyJourney.domain.auth.dto.ModifyInfoReq;
import com.ll.JollyJourney.domain.auth.dto.SignInReq;
import com.ll.JollyJourney.domain.auth.dto.SignUpReq;
import com.ll.JollyJourney.domain.auth.service.AuthService;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthController {
    private final AuthService authService;

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInReq request) {
        return ResponseEntity.ok(authService.signIn(request));
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpReq request) {
        return ResponseEntity.ok(authService.signUp(request));
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/modify")
    public ResponseEntity<Member> modifyInfo(@RequestBody ModifyInfoReq request) {
        Member updatedMember = authService.modifyInfo(request);
        return ResponseEntity.ok(updatedMember);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/password/update")
    public ResponseEntity<Member> changePassword(@RequestBody ChangePasswordReq request) {
        Member updatedMember = authService.changePassword(request);
        return ResponseEntity.ok(updatedMember);
    }


}
