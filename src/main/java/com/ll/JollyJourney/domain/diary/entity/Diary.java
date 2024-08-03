package com.ll.JollyJourney.domain.diary.entity;

import com.ll.JollyJourney.domain.diary.dto.DiaryReq;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.sql.Time;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userDId;

    @Comment("명상 기록")
    private String meditationReport;

    @Comment("감사 기록")
    private String thanksReport;

    @Comment("운동 기록")
    private String exerciseReport;

    @Comment("운동 시간")
    private String exerciseTime;

    @Comment("수면 시간")
    private Time sleepTime;

    @Comment("수면 질")
    private String sleepQuality;

    @Comment("육아 기록")
    private String kidReport;

    @Comment("몸 상태 카테고리")
    @Enumerated(value = EnumType.STRING)
    private BodyStatus bodyStatus;

    @Comment("감정 카테고리")
    @Enumerated(value = EnumType.STRING)
    private EmotionStatus emotionStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;

    public void update(DiaryReq request) {
        this.meditationReport = request.meditationReport();
        this.thanksReport = request.thanksReport();
        this.exerciseReport = request.exerciseReport();
        this.exerciseTime = request.exerciseTime();
        this.sleepTime = request.sleepTime();
        this.sleepQuality = request.sleepQuality();
        this.kidReport = request.kidReport();
        this.bodyStatus = request.bodyStatus();
        this.emotionStatus = request.emotionStatus();
    }

}
