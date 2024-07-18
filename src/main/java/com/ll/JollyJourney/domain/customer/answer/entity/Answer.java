package com.ll.JollyJourney.domain.customer.answer.entity;

import com.ll.JollyJourney.domain.customer.question.entity.Question;
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
import org.hibernate.annotations.Comment;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {
    @Comment("문의 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Comment("회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("문의 답변 내용")
    private String answerContent;
}