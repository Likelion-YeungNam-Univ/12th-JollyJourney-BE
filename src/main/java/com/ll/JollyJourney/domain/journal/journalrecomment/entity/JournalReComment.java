package com.ll.JollyJourney.domain.journal.journalrecomment.entity;

import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JournalReComment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private JournalComment journalComment;

    private String reComment;
    private int likes;
}
