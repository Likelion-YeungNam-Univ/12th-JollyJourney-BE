package com.ll.JollyJourney.domain.likes.dto;

import com.ll.JollyJourney.domain.likes.entity.Likes;

public record LikesRes(
        Long likesId,
        Long memberId,
        Long journalId,
        String message,
        int likesCount
) {
    public static LikesRes fromEntity(Likes likes, String message, int likesCount) {
        return new LikesRes(
                likes.getLikesId(),
                likes.getMember().getUserId(),
                likes.getJournal().getJournalId(),
                message,
                likesCount
        );
    }
}
