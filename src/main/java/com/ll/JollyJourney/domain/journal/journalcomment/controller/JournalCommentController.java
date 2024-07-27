package com.ll.JollyJourney.domain.journal.journalcomment.controller;
import com.ll.JollyJourney.domain.journal.journalcomment.dto.JournalCommentDto;
import com.ll.JollyJourney.domain.journal.journalcomment.service.JournalCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String deleteComment(@PathVariable Long journalId,
                                @RequestParam Long commentId) {
        journalCommentService.deleteComment(commentId);
        return "redirect:/journal/detail/" + journalId;
    }

}