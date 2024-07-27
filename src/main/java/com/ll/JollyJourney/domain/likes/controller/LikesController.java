package com.ll.JollyJourney.domain.likes.controller;

import com.ll.JollyJourney.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {

    private final LikesService likesService;

    @PostMapping
    public String addLike(@RequestParam Long journalId, @RequestParam Long memberId) {
        likesService.likeJournal(journalId, memberId);
        return "redirect:/journal/detail/" + journalId;
    }

    @DeleteMapping
    public String removeLike(@RequestParam Long journalId, @RequestParam Long memberId) {
        likesService.likeJournal(journalId, memberId);
        return "redirect:/journal/detail/" + journalId;
    }
}