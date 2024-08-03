package com.ll.JollyJourney.domain.diary.dto;

import com.ll.JollyJourney.domain.diary.entity.BodyStatus;
import com.ll.JollyJourney.domain.diary.entity.Diary;
import com.ll.JollyJourney.domain.diary.entity.EmotionStatus;
import com.ll.JollyJourney.domain.member.member.entity.Member;

import java.sql.Time;

public record DiaryReq(
        Long userDId,
        String meditationReport,
        String thanksReport,
        String exerciseReport,
        String exerciseTime,
        Time sleepTime,
        String sleepQuality,
        String kidReport,
        BodyStatus bodyStatus,
        EmotionStatus emotionStatus
) {
    public Diary toEntity(Member member) {
        return Diary.builder()
                .meditationReport(this.meditationReport)
                .thanksReport(this.thanksReport)
                .exerciseReport(this.exerciseReport)
                .exerciseTime(this.exerciseTime)
                .sleepTime(this.sleepTime)
                .sleepQuality(this.sleepQuality)
                .kidReport(this.kidReport)
                .bodyStatus(this.bodyStatus)
                .emotionStatus(this.emotionStatus)
                .member(member)
                .build();
    }

}
