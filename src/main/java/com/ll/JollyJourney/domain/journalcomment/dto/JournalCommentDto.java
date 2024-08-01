package com.ll.JollyJourney.domain.journalcomment.dto;

public class JournalCommentDto {
    private final Long journalId;
    private final Long memberId;
    private final String comment;

    public JournalCommentDto(Long journalId, Long memberId, String comment) {
        this.journalId = journalId;
        this.memberId = memberId;
        this.comment = comment;
    }
    public Long getJournalId() {
        return journalId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getComment() {
        return comment;
    }
}