package com.ll.JollyJourney.domain.member.member.dto.kakao;


import com.ll.JollyJourney.global.enums.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class KakaoInfo {

    private String id;
    private String name;
    private String email;
    private Gender gender;
    private LocalDate birthday;
    private String phoneNumber;


    public KakaoInfo(String id, String name, String email, String gender, String birthYear, String birthMonthAndDay, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.gender = null;
        this.birthday = null;
        this.phoneNumber = null;
    }
}
