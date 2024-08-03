package com.ll.JollyJourney.domain.likes.controller;

import com.ll.JollyJourney.domain.likes.dto.LikesReq;
import com.ll.JollyJourney.domain.likes.dto.LikesRes;
import com.ll.JollyJourney.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {
    private final LikesService likesService;
    @PostMapping
    public ResponseEntity<LikesRes> toggleLikes(@RequestBody LikesReq likesReq, Authentication authentication) {
        LikesRes response = likesService.toggleLikes(likesReq, authentication);
        return ResponseEntity.ok(response);
    }
}
