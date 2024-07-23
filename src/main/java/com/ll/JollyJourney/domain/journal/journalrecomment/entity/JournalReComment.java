package com.ll.JollyJourney.domain.journal.journalrecomment.entity;

import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class JournalReComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalReCoId;

    @ManyToOne(fetch = FetchType.LAZY)
    private JournalComment journalComment;

    private String reComment;
    private int likes;
}
