package com.ll.JollyJourney.domain.journal.dto;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoRes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record JournalRes(
        Long journalId,
        String title,
        String content,
        String imageUrl,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        int likesCount,
        int viwCount,
        int commentCount,
        List<JournalCoRes> comments
) {
    public static JournalRes of(Long journalId, String title, String content, String imageUrl, LocalDateTime createDate, LocalDateTime modifyDate, int likesCount, int viwCount, int commentCount, List<JournalCoRes> comments) {
        return new JournalRes(journalId, title, content, imageUrl, createDate, modifyDate, likesCount, viwCount, commentCount, comments);
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
                journal.getViewCount(),
                journal.getCommentCount(),
                journal.getComments().stream()
                        .map(JournalCoRes::fromEntity)
                        .collect(Collectors.toList())
        );
    }
}