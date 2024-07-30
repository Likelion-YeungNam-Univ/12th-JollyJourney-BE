package com.ll.JollyJourney.domain.journal.journalcomment.controller;

import com.ll.JollyJourney.domain.journal.journalcomment.dto.JournalCommentDto;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journal.journalcomment.service.JournalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/journal/detail")
public class JournalCommentController {

    @Autowired
    private JournalCommentService journalCommentService;

    @PostMapping("/{journalId}/comments")
    public String addComment(@PathVariable Long journalId,
                             @RequestParam Long memberId,
                             @RequestParam String comment) {
        JournalCommentDto journalCommentDto = new JournalCommentDto(journalId, memberId, comment);
        journalCommentService.addComment(journalCommentDto);
        return "redirect:/journal/detail/" + journalId;
    }

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
}