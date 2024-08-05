package com.ll.JollyJourney.domain.likes.dto;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.likes.entity.Likes;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import jakarta.validation.constraints.NotNull;

public record LikesReq(
        @NotNull Long journalId
) {
    public Likes toEntity(Journal journal, Member member) {
        return Likes.builder()
                .member(member)
                .journal(journal)
                .build();
    }
}

