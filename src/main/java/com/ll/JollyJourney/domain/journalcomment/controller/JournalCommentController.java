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

    @PostMapping("")
    public ResponseEntity<JournalCoRes> createComment(@RequestBody JournalCoReq journalCoReq){
        JournalComment journalComment = journalCommentService.createComment(journalCoReq);
        JournalCoRes journalCoRes= JournalCoRes.fromEntity(journalComment);
        return ResponseEntity.ok(journalCoRes);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<JournalCoRes> updateComment(@PathVariable Long id, @RequestBody JournalCoReq journalCoReq, Authentication authentication) {
        JournalComment journalComment = journalCommentService.updateComment(id, journalCoReq);
        JournalCoRes journalCoRes = JournalCoRes.fromEntity(journalComment);
        return ResponseEntity.ok(journalCoRes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JournalCoRes> deleteComment(@PathVariable Long id){
        journalCommentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }
}

/*
    @PostMapping("/{journalId}/comments/delete")
    @PreAuthorize("isAuthenticated()")
    public String deleteComment(@PathVariable Long journalId,
                                @RequestParam Long commentId,
                                Principal principal) {
        if (!journalCommentService.isAuthor(commentId)) {
            throw new AccessDeniedException("You are not authorized to delete this comment");
        }
        journalCommentService.deleteComment(commentId);
        return "redirect:/journal/detail/" + journalId;
    }

    @GetMapping("/{journalId}/comments/modify/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public String modifyCommentForm(@PathVariable Long journalId,
                                    @PathVariable Long commentId,
                                    Model model,
                                    Principal principal) {
        if (!journalCommentService.isAuthor(commentId)) {
            throw new AccessDeniedException("You are not authorized to modify this comment");
        }
        JournalComment comment = journalCommentService.getCommentById(commentId);
        model.addAttribute("commentToEdit", comment);
        model.addAttribute("journalId", journalId);
        return "domain/comment/commentForm";
    }

    @PostMapping("/{journalId}/comments/modify")
    @PreAuthorize("isAuthenticated()")
    public String modifyComment(@PathVariable Long journalId,
                                @RequestParam Long commentId,
                                @RequestParam String comment,
                                Principal principal) {
        if (!journalCommentService.isAuthor(commentId)) {
            throw new AccessDeniedException("You are not authorized to modify this comment");
        }
        journalCommentService.updateComment(commentId, comment);
        return "redirect:/journal/detail/" + journalId;
    }
 */