package com.ll.JollyJourney.domain.member.member.controller;

import com.ll.JollyJourney.domain.member.member.dto.JoinRequest;
import com.ll.JollyJourney.domain.member.member.dto.MemberResponse;
import com.ll.JollyJourney.domain.member.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(" /members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Void> read(@Valid @RequestBody JoinRequest joinRequest) {
        memberService.join(joinRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<MemberResponse> getMember(@PathVariable String email) {
        MemberResponse memberResponse = memberService.getMemberResponse(email);
        return ResponseEntity.ok(memberResponse);
    }

//    @PutMapping("/modify")
//    public ResponseEntity<Void> modify(@Valid @RequestBody ModifyRequest modifyRequest,
//                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
//        this.securityUser = securityUser;
//        memberService.modify(modifyRequest, securityUser.getMember());
//        return ResponseEntity.ok().build();
//    }


}
//이거 아님
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/member")
//public class MemberController {
//
//    private final MemberService memberService;
//    private final JwtUtil jwtUtil;
//
//    // 회원가입
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody MemberDto memberDto) {
//        try {
//            memberService.registerMember(memberDto);
//            return ResponseEntity.ok("회원가입 성공");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // 이메일로 회원 조회
//    @GetMapping("/email/{email}")
//    public ResponseEntity<?> getByEmail(HttpServletRequest request, @PathVariable String email) {
//        Long userId = jwtUtil.getUserId(jwtUtil.resolveToken(request).substring(7));
//        Member member = memberService.findByEmail(email);
//        if (!userId.equals(member.getUserId())) {
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.ok(member);
//    }
////
////    // 전화번호로 회원 조회
////    @GetMapping("/phone/{phoneNumber}")
////    public ResponseEntity<?> getByPhoneNumber(HttpServletRequest request, @PathVariable String phoneNumber) {
////        Long userId = jwtUtil.getUserId(jwtUtil.resolveToken(request).substring(7));
////        Member member = memberService.findByPhoneNumber(phoneNumber);
////        if (!userId.equals(member.getUserId())) {
////            return ResponseEntity.badRequest().build();
////        }
////        return ResponseEntity.ok(member);
////    }
//
//    // 회원 정보 수정
//    @PutMapping("/{userId}")
//    public ResponseEntity<String> update(@PathVariable Long userId, @RequestBody MemberDto memberDto) {
//        try {
//            memberService.updateMember(userId, memberDto);
//            return ResponseEntity.ok("회원 정보 수정 성공");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//
//    // 회원 탈퇴
//    @DeleteMapping("/{userId}")
//    public ResponseEntity<String> delete(@PathVariable Long userId) {
//        try {
//            memberService.deleteMember(userId);
//            return ResponseEntity.ok("회원 탈퇴 성공");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
