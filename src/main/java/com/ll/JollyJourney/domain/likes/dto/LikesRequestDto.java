package com.ll.JollyJourney.domain.likes.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikesRequestDto {

    private Long memberId;
    private Long journalId;

    public LikesRequestDto(Long memberId, Long journalId) {
        this.memberId = memberId;
        this.journalId = journalId;
    }
}