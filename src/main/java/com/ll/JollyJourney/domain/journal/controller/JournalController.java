package com.ll.JollyJourney.domain.journal.controller;

import com.ll.JollyJourney.domain.journal.dto.JournalReq;
import com.ll.JollyJourney.domain.journal.dto.JournalRes;
import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.service.JournalService;
import com.ll.JollyJourney.domain.journalcomment.service.JournalCommentService;
import com.ll.JollyJourney.global.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/journals")
@RequiredArgsConstructor
@Tag(name = "[정보글 관련 API]", description = "정보글 관련 CRUD API")
public class JournalController {
    private final JournalService journalService;
    private final JournalCommentService journalCommentService;
    private final S3Service s3Service;

    @Operation(summary = "정보글 전체 조회", description = "정보글 전체 조회")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보글 전체 조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    [
                        {
                            "journalId": 1,
                            "title": "테스트 데이터입니다:[001]",
                            "content": "내용무",
                            "createDate": "2024-07-23T21:51:49.252343",
                            "modifyDate": "2024-08-03T12:15:17.442176",
                            "likesCount": 0,
                            "commentCount": 2
                        },
                        {
                            "journalId": 2,
                            "title": "테스트 데이터입니다:[002]",
                            "content": "내용무",
                            "createDate": "2024-07-23T21:51:49.415665",
                            "modifyDate": "2024-07-23T21:51:49.42922",
                            "likesCount": 0,
                            "commentCount": 0
                        }
                    ]
                    """))),
            @ApiResponse(responseCode = "400", description = "전체 정보글 조회 실패")
    })
    @GetMapping("")
    public ResponseEntity<List<JournalRes>> getAllJournals() {
        List<JournalRes> journals = journalService.getAllJournals();
        return ResponseEntity.ok(journals);
    }

    @Operation(summary = "특정 정보글 조회", description = "journalId로 특정 게시물을 조회하는 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보글 조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                                "journalId": 1,
                                "title": "테스트 데이터입니다:[001]",
                                "content": "내용무",
                                "createDate": "2024-07-23T21:51:49.252343",
                                "modifyDate": "2024-08-03T12:15:17.442176",
                                "likesCount": 0,
                                "commentCount": 2
                            }
                            """))),
            @ApiResponse(responseCode = "400", description = "정보글 조회 실패")
    })
    @GetMapping("/{id}")
    public ResponseEntity<JournalRes> getJournal(@PathVariable Long id) {
        Journal journal = journalService.getJournal(id);
        JournalRes journalRes = JournalRes.fromEntity(journal);
        return ResponseEntity.ok(journalRes);
    }

    @Operation(summary = "정보글 생성", description = "새로운 정보글을 생성합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보글 생성 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                                "journalId": 1,
                                "title": "새로운 정보글 제목",
                                "content": "새로운 정보글 내용",
                                "createDate": "2024-07-23T21:51:49.252343",
                                "modifyDate": "2024-08-03T12:15:17.442176",
                                "likesCount": 0,
                                "commentCount": 0
                            }
                            """))),
            @ApiResponse(responseCode = "400", description = "정보글 생성 실패")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Journal> createJournal(
            @Parameter(description = "정보글 제목", required = true) @RequestParam("title") String title,
            @Parameter(description = "정보글 내용", required = true) @RequestParam("content") String content,
            @Parameter(description = "사용자 ID", required = true) @RequestParam("userId") Long userId,
            @Parameter(description = "이미지 파일", required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary"))) @RequestPart("image") MultipartFile image) {
        try {
            // 이미지 업로드
            String imageUrl = s3Service.uploadFile(image);

            // 게시글 생성
            JournalReq request = new JournalReq(title, content, userId, image);
            Journal journal = request.toEntity(imageUrl);
            Journal savedJournal = journalService.createJournal(request, imageUrl);

            return ResponseEntity.ok(savedJournal);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "정보글 수정", description = "기존 정보글을 수정합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보글 수정 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                                "journalId": 1,
                                "title": "수정된 정보글 제목",
                                "content": "수정된 정보글 내용",
                                "createDate": "2024-07-23T21:51:49.252343",
                                "modifyDate": "2024-08-03T12:15:17.442176",
                                "likesCount": 0,
                                "commentCount": 0
                            }
                            """))),
            @ApiResponse(responseCode = "400", description = "정보글 수정 실패")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/modify/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<JournalRes> updateJournal(
            @PathVariable Long id,
            @Parameter(description = "정보글 제목", required = true) @RequestParam("title") String title,
            @Parameter(description = "정보글 내용", required = true) @RequestParam("content") String content,
            @Parameter(description = "사용자 ID", required = true) @RequestParam("userId") Long userId,
            @Parameter(description = "이미지 파일", required = false, content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, schema = @Schema(type = "string", format = "binary"))) @RequestPart(value = "image", required = false) MultipartFile image) {
        try {
            // 이미지 업로드
            String imageUrl = null;
            if (image != null) {
                imageUrl = s3Service.uploadFile(image);
            }
            // 게시글 수정
            JournalReq request = new JournalReq(title, content, userId, image);
            Journal journal = journalService.updateJournal(id, request, imageUrl);
            JournalRes journalRes = JournalRes.fromEntity(journal);
            return ResponseEntity.ok(journalRes);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "정보글 삭제", description = "기존 정보글을 삭제합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정보글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "정보글 삭제 실패")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJournal(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Authenticated user: " + userDetails.getUsername());
        try {
            journalService.deleteJournal(id);
            return ResponseEntity.ok("정보글 삭제 완료.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }
}