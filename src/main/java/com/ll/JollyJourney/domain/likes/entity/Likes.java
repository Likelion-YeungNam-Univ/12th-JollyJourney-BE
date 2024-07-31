package com.ll.JollyJourney.domain.likes.entity;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"journalId", "memberId"}
                )
        }
)
public class Likes  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likesId;

    @JoinColumn(name="memberId")
    @ManyToOne
    private Member member;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="journalId")
    @ManyToOne
    private Journal journal;
}
