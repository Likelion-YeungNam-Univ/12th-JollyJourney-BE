package com.ll.JollyJourney.domain.journal.dto;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import java.time.LocalDateTime;

public record JournalListRes(
        Long journalId,
        String title,
        String content,
        String imageUrl,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int likesCount,
        int viwCount,
        int commentCount
) {
    public static JournalListRes fromEntity(Journal journal) {
        return new JournalListRes(
                journal.getJournalId(),
                journal.getTitle(),
                journal.getContent(),
                journal.getImageUrl(),
                journal.getCreateDate(),
                journal.getModifyDate(),
                journal.getLikesCount(),
                journal.getViewCount(),
                journal.getCommentCount()
        );
    }
}