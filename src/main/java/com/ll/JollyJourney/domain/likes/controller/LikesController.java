package com.ll.JollyJourney.domain.likes.controller;

import com.ll.JollyJourney.domain.likes.dto.LikesReq;
import com.ll.JollyJourney.domain.likes.dto.LikesRes;
import com.ll.JollyJourney.domain.likes.service.LikesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
@Tag(name = "[좋아요 관련 API]", description = "좋아요 관련 API")
public class LikesController {
    private final LikesService likesService;
    @Operation(summary = "좋아요 기능", description = "좋아요, 좋아요 취소",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "좋아요, 좋아요 취소",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "좋아요 실패")
    })
    @PostMapping
    public ResponseEntity<LikesRes> toggleLikes(@RequestBody LikesReq likesReq, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("User is not authenticated");
        }
        LikesRes response = likesService.toggleLikes(likesReq, authentication);
        return ResponseEntity.ok(response);
    }
}
