package com.ll.JollyJourney.domain.journal.journalcomment.dto;

import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;

import java.time.LocalDateTime;

public record JournalCoRes(
        Long commentId,
        String content,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        Long journalId,
        Long userId
) {
    public static JournalCoRes fromEntity(JournalComment journalComment) {
        return new JournalCoRes(
                journalComment.getJournalCoId(),
                journalComment.getContent(),
                journalComment.getCreateDate(),
                journalComment.getModifyDate(),
                journalComment.getJournal().getJournalId(),
                journalComment.getMember().getUserId()
        );
    }
}