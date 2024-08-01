package com.ll.JollyJourney.domain.diary.entity;

public enum BodyStatus {
    HEADACHE("HEADACHE"),
    STOMACHACHE("STOMACHACHE"),
    BACKPAIN("BACKPAIN"),
    SKINTROBLUE("SKINTROBLUE"),
    CONSTIPATION("CONSTIPATION"), // 변비
    LOSS_OF_APPETITE("Loss of appetite"), // 식욕 부진
    INDIGESTION("INDIGESTION"), // 소화불량
    ABDOMINAL_BLOATING("ABDOMINAL_BLOATING"), // 복부 팽만
    MUSCLE_PAIN("MUSCLE PAIN"), //근육통
    CHILLS("CHILLS"), // 오한
    FEVER("FEVER"), // 발열
    SLEEP_DISORDER("SLEEP_DISORDER"), // 수면 장애
    ETC("ETC");
    private String value;


    BodyStatus(String value) {
    }

}
