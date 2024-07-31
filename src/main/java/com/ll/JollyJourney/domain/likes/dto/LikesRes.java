package com.ll.JollyJourney.domain.likes.dto;

import com.ll.JollyJourney.domain.likes.entity.Likes;

public record LikesRes(
        Long likesId,
        Long memberId,
        Long journalId
) {
    public static LikesRes fromEntity(Likes likes) {
        return new LikesRes(
                likes.getLikesId(),
                likes.getMember().getUserId(),
                likes.getJournal().getJournalId()
        );
    }
}
