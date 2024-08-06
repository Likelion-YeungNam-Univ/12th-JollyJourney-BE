package com.ll.JollyJourney.domain.diary.dto;

import com.ll.JollyJourney.domain.diary.entity.BodyStatus;
import com.ll.JollyJourney.domain.diary.entity.Diary;
import com.ll.JollyJourney.domain.diary.entity.EmotionStatus;

import java.sql.Time;
import java.time.LocalDateTime;

public record DiaryRes(
        Long userDId,
        String userDate,
        String meditationReport,
        String thanksReport,
        String exerciseReport,
        String exerciseTime,
        Time sleepTime,
        String sleepQuality,
        String kidReport,
        BodyStatus bodyStatus,
        EmotionStatus emotionStatus,
        LocalDateTime createDate,
        LocalDateTime modifyDate,
        Long userId
) {
    public static DiaryRes fromEntity(Diary diary) {
        return new DiaryRes(
                diary.getUserDId(),
                diary.getUserDate().toString(),
                diary.getMeditationReport(),
                diary.getThanksReport(),
                diary.getExerciseReport(),
                diary.getExerciseTime(),
                diary.getSleepTime(),
                diary.getSleepQuality(),
                diary.getKidReport(),
                diary.getBodyStatus(),
                diary.getEmotionStatus(),
                diary.getCreateDate(),
                diary.getModifyDate(),
                diary.getMember().getUserId()
        );
    }
}
