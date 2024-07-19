package com.ll.JollyJourney.domain.likes.entity;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.post.post.entity.Post;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"postId","journalId", "memberId"}
                )
        }
)
public class Likes extends BaseEntity {
    @JoinColumn(name="memberId")
    @ManyToOne
    private Member member;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="journalId")
    @ManyToOne
    private Journal journal;

//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name="journalId")
//    @ManyToOne
//    private JournalComment journalComment;
//
//    @OnDelete(action = OnDeleteAction.CASCADE)
//    @JoinColumn(name="journalId")
//    @ManyToOne
//    private JournalReComment journalReComment; // 댓글에도 좋아요 기능을 넣을 것인가?

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="postId")
    @ManyToOne
    private Post post;


}
