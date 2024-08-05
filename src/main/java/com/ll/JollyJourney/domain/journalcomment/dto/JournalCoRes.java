package com.ll.JollyJourney.domain.journalcomment.dto;

import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;

import java.time.LocalDateTime;

public record JournalCoRes(
        String username,
        Long commentId,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        Long journalId
) {
    public static JournalCoRes fromEntity(JournalComment journalComment) {
        return new JournalCoRes(
                journalComment.getMember().getName(),
                journalComment.getJournalCoId(),
                journalComment.getContent(),
                journalComment.getCreateDate(),
                journalComment.getModifyDate(),
                journalComment.getJournal().getJournalId()
        );
    }
}