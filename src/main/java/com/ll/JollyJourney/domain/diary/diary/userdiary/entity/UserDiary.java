package com.ll.JollyJourney.domain.diary.diary.userdiary.entity;

import com.ll.JollyJourney.domain.post.post.entity.PostCategory;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.sql.Time;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDiary extends BaseEntity {

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


}
