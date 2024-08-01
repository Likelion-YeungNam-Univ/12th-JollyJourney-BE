package com.ll.JollyJourney.domain.member.mypage.controller;


import com.ll.JollyJourney.domain.member.member.dto.ModifyRequest;
import com.ll.JollyJourney.domain.member.member.service.MemberService;
import com.ll.JollyJourney.global.security.authentication.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MypageController {
    private final MemberService memberService;
    @GetMapping("/profile")
    public ResponseEntity<?> profileForm(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long id = customUserDetails.getMemberId(); // 로그인한 유저의 id를 알려줌

        return ResponseEntity.ok(id);
    }

    @PostMapping("/profile")
    public String modify(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                         @Valid ModifyRequest modifyRequest,
                         BindingResult bindingResult
    ) {
        return "";
    }
}
