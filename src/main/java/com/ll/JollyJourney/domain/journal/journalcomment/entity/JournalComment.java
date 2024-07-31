package com.ll.JollyJourney.domain.journal.journalcomment.entity;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JournalComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalCoId;

    @JoinColumn(name = "journal_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Journal journal;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(length = 100)
    private String content;

    public void setContent(String content) {
        this.content = content;
    }
}