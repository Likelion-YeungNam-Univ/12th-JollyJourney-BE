package com.ll.JollyJourney.domain.likes.controller;

import com.ll.JollyJourney.domain.likes.dto.LikesReq;
import com.ll.JollyJourney.domain.likes.dto.LikesRes;
import com.ll.JollyJourney.domain.likes.entity.Likes;
import com.ll.JollyJourney.domain.likes.service.LikesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/likes")
public class LikesController {
    @Autowired
    private final LikesService likesService;

    @PostMapping
    public ResponseEntity<LikesRes> addLike(@RequestBody LikesReq likesReq) {
        Likes likes = likesService.addLikes(likesReq);
        LikesRes likesRes = LikesRes.fromEntity(likes);
        return ResponseEntity.ok(likesRes);
    }

    @DeleteMapping("/{likesId}")
    public ResponseEntity<Void> removeLike(@PathVariable Long likesId) {
        likesService.deleteLikes(likesId);
        return ResponseEntity.ok().build();
    }
}

/*
    @DeleteMapping
    public String removeLike(@RequestParam Long journalId, @RequestParam Long memberId) {
        likesService.likeJournal(journalId, memberId);
        return "redirect:/journal/detail/" + journalId;
    }
 */