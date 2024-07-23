package com.ll.JollyJourney.domain.customer.question.entity;

import com.ll.JollyJourney.domain.customer.answer.entity.Answer;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerQId;

    @Comment("문의 카테고리")
    @Enumerated(value = EnumType.STRING)
    private QuestionCategory questionCategory;

    @Comment("회원 정보")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Comment("문의 제목")
    private String questionTitle;

    @Comment("문의 내용")
    private String questionContent;

    @Comment("답변 목록")
    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    private List<Answer> answers = new ArrayList<>();


}

