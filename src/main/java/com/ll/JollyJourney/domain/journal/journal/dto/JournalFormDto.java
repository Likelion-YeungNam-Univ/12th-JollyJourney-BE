package com.ll.JollyJourney.domain.journal.journal.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class JournalFormDto {
    private Long journalId;

    @NotEmpty(message = "제목은 필수항목입니다.")
    @Size(max = 200, message = "제목은 최대 200자까지 입력할 수 있습니다.")
    private String title;

    @NotEmpty(message = "내용은 필수항목입니다.")
    private String content;

    // 검색
    private String keyword; // 검색 키워드
    private String type; // 검색 타입

    public JournalFormDto() {
    }

    public JournalFormDto(Long journalId, String title, String content) {
        this.journalId = journalId;
        this.title = title;
        this.content = content;
    }

    public Long getJournalId() {
        return journalId;
    }

    public void setJournalId(Long journalId) {
        this.journalId = journalId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}