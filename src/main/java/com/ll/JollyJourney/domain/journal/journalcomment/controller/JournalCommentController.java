package com.ll.JollyJourney.domain.journal.journalcomment.controller;

import com.ll.JollyJourney.domain.journal.journalcomment.dto.JournalCoReq;
import com.ll.JollyJourney.domain.journal.journalcomment.dto.JournalCoRes;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journal.journalcomment.service.JournalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        JournalCoRes journalCoRes = journalCommentService.getComment(id);
        return ResponseEntity.ok(id);
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody JournalCoReq journalCoReq){
        JournalComment journalComment = journalCommentService.createComment(journalCoReq);
        JournalCoRes journalCoRes= JournalCoRes.fromEntity(journalComment);
        return ResponseEntity.ok(journalCoRes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id, @RequestBody JournalCoReq journalCoReq){
        JournalComment journalComment = journalCommentService.updateComment(id, journalCoReq);
        JournalCoRes journalCoRes = JournalCoRes.fromEntity(journalComment);
        return ResponseEntity.ok(journalCoRes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id){
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