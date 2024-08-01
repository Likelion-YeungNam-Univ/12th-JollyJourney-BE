package com.ll.JollyJourney.domain.diary.entity;

public enum EmotionStatus {
    HAPPINESS("HAPPINESS"), //행복
    ANXIOUS ("ANXIOUS"), // 불안
    SENSITIVE("SENSITIVE"), // 예민
    DEPRESSION("DEPRESSION"), //우울
    LETHARGY("LETHARGY"), // 무기력함
    ANXIETY("ANXIETY"); // 걱정

    private String value;

    EmotionStatus(String value) {
    }
}