package com.ll.JollyJourney.domain.journalcomment.controller;

import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoReq;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoRes;
import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journalcomment.service.JournalCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/journal/{journalId}/comments")
@RequiredArgsConstructor
@Tag(name = "[댓글 관련 API]", description = "댓글 관련 CRUD API")
public class JournalCommentController {

    private final JournalCommentService journalCommentService;

    @Operation(summary = "댓글 전체 조회", description = "특정 정보글의 모든 댓글을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 전체 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "댓글 조회 실패")
    })
    @GetMapping("")
    public ResponseEntity<List<JournalCoRes>> getAllComments(@PathVariable Long journalId) {
        List<JournalCoRes> comments = journalCommentService.getAllComments(journalId);
        return ResponseEntity.ok(comments);
    }

    @Operation(summary = "특정 댓글 조회", description = "commentId로 특정 댓글을 조회하는 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 조회 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "댓글 조회 실패")
    })
    @GetMapping("/{commentId}")
    public ResponseEntity<JournalCoRes> getComment(@PathVariable Long journalId, @PathVariable Long commentId) {
        JournalCoRes journalCoRes = journalCommentService.getComment(commentId);
        return ResponseEntity.ok(journalCoRes);
    }

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 생성 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "댓글 생성 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<JournalComment> createComment(@RequestBody JournalCoReq request, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("User is not authenticated");
        }
        JournalComment journalComment = journalCommentService.createComment(request, authentication);
        return ResponseEntity.ok(journalComment);
    }

    @Operation(summary = "댓글 수정", description = "기존 댓글을 수정합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 수정 성공",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "댓글 수정 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @PutMapping("/modify/{id}")
    public ResponseEntity<JournalCoRes> updateComment(@PathVariable Long journalId, @PathVariable Long id, @RequestBody JournalCoReq journalCoReq, Authentication authentication) {
        JournalComment journalComment = journalCommentService.updateComment(id, journalCoReq, authentication);
        JournalCoRes journalCoRes = JournalCoRes.fromEntity(journalComment);
        return ResponseEntity.ok(journalCoRes);
    }

    @Operation(summary = "댓글 삭제", description = "기존 댓글을 삭제합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "댓글 삭제 실패"),
            @ApiResponse(responseCode = "403", description = "권한 없음")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long journalId, @PathVariable Long id, Authentication authentication) {
        try {
            journalCommentService.deleteComment(id, authentication);
            return ResponseEntity.ok("댓글 삭제 완료");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(403).body(ex.getMessage());
        }
    }
}