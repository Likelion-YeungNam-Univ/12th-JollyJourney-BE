package com.ll.JollyJourney.domain.journal.dto;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import org.springframework.web.multipart.MultipartFile;

public record JournalReq(
        @NotNull String title,
        String content,
        MultipartFile image
) {
    public Journal toEntity(String imageUrl){
        return Journal.builder()
                .title(this.title)
                .content(this.content)
                .imageUrl(imageUrl)
                .build();
    }
}