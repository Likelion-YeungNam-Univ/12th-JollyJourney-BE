package com.ll.JollyJourney.domain.journal.dto;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import java.time.LocalDateTime;

public record JournalRes(
        Long journalId,
        String title,
        String content,
        String imageUrl,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int likesCount,
        int commentCount
) {
    public static JournalRes of(Long journalId, String title, String content, String imageUrl, LocalDateTime createDate, LocalDateTime modifyDate, int likesCount, int commentCount) {
        return new JournalRes(journalId, title, content, imageUrl, createDate, modifyDate, likesCount, commentCount);
    }

    public static JournalRes fromEntity(Journal journal) {
        return new JournalRes(
                journal.getJournalId(),
                journal.getTitle(),
                journal.getContent(),
                journal.getImageUrl(), // Added imageUrl mapping
                journal.getCreateDate(),
                journal.getModifyDate(),
                journal.getLikesCount(),
                journal.getCommentCount()
        );
    }
}