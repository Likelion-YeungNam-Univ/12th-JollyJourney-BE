package com.ll.JollyJourney.domain.journal.dto;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public record JournalReq(
        @NotNull String title,
        String content,
        Long userId
) {
    public Journal toEntity(){
        return Journal.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}