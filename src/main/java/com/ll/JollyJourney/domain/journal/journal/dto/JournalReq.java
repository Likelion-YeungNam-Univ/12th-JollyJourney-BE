package com.ll.JollyJourney.domain.journal.journal.dto;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public record JournalReq(
        @NotNull String title,
        String content,
        Long memberId
) {
    public Journal toEntity(Member member){
        return Journal.builder()
                .member(member)
                .title(this.title)
                .content(this.content)
                .build();
    }
}