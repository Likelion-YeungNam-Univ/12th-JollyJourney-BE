package com.ll.JollyJourney.domain.customer.answer.entity;

import com.ll.JollyJourney.domain.customer.question.entity.Question;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerAId;

    @Comment("문의 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Comment("회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("문의 답변 내용")
    private String answerContent;

    public void changeAnswer(String answerContent) {

        this.answerContent = answerContent;
    }
}