package com.ll.JollyJourney.domain.journal.journalcomment.dto;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;

public record JournalCoReq(

        @NotNull String content,
        @NotNull Long journalId,
        @NotNull Long userId
) {
    public JournalComment toEntity(Journal journal, Member member) {
        return JournalComment.builder()
                .content(this.content)
                .journal(journal)
                .member(member)
                .build();
    }
}
