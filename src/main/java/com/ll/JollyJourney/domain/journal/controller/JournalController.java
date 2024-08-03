package com.ll.JollyJourney.domain.journal.controller;

import com.ll.JollyJourney.domain.journal.dto.JournalReq;
import com.ll.JollyJourney.domain.journal.dto.JournalRes;
import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.service.JournalService;
import com.ll.JollyJourney.domain.journalcomment.service.JournalCommentService;
import com.ll.JollyJourney.global.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/journals")
@RequiredArgsConstructor
// @Tag(name = "[정보글 관련 API]", description = "정보글 관련 CRUD API")
public class JournalController {
    private final JournalService journalService;
    private final JournalCommentService journalCommentService;
    private final S3Service s3Service;

    @GetMapping("")
    public ResponseEntity<?> getAllJournals(){
        List<JournalRes> journals = journalService.getAllJournals();
        return ResponseEntity.ok(journals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalRes> getJournal(@PathVariable Long id) {
        Journal journal = journalService.getJournal(id);
        JournalRes journalRes = JournalRes.fromEntity(journal);
        return ResponseEntity.ok(journalRes);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<Journal> createJournal(@ModelAttribute JournalReq request) {
        try {
            // 이미지 업로드
            String imageUrl = s3Service.uploadFile(request.image());

            // 게시글 생성
            Journal journal = request.toEntity(imageUrl);
            Journal savedJournal = journalService.createJournal(request, imageUrl);

            return ResponseEntity.ok(savedJournal);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("modify/{id}")
    public ResponseEntity<JournalRes> updateJournal(@PathVariable Long id, @RequestBody JournalReq journalReq){
        Journal journal = journalService.updateJournal(id, journalReq);
        JournalRes journalRes = JournalRes.fromEntity(journal);
        return ResponseEntity.ok(journalRes);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteJournal(@PathVariable Long id) {
        try {
            journalService.deleteJournal(id);
            return ResponseEntity.ok("정보글 삭제 완료.");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }
}
