package com.ll.JollyJourney.domain.journalcomment.controller;

import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoReq;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoRes;
import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journalcomment.service.JournalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/journal/{journalId}/comments")
public class JournalCommentController {
    @Autowired
    private JournalCommentService journalCommentService;

    @GetMapping("")
    public ResponseEntity<?> getAllComments(){
        List<JournalCoRes> comments = journalCommentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<JournalCoRes> getComment(@PathVariable Long commentId) {
        JournalCoRes journalCoRes = journalCommentService.getComment(commentId);
        return ResponseEntity.ok(journalCoRes);
    }

    @PostMapping("/create")
    public ResponseEntity<JournalComment> createComment(@RequestBody JournalCoReq request, Authentication authentication) {
        JournalComment journalComment = journalCommentService.createComment(request, authentication);
        return ResponseEntity.ok(journalComment);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<JournalCoRes> updateComment(@PathVariable Long id, @RequestBody JournalCoReq journalCoReq, Authentication authentication) {
        JournalComment journalComment = journalCommentService.updateComment(id, journalCoReq, authentication);
        JournalCoRes journalCoRes = JournalCoRes.fromEntity(journalComment);
        return ResponseEntity.ok(journalCoRes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<JournalCoRes> deleteComment(@PathVariable Long id, Authentication authentication){
        journalCommentService.deleteComment(id, authentication);
        return ResponseEntity.ok().build();
    }
}
