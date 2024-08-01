package com.ll.JollyJourney.domain.member.member.dto;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.enums.Gender;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberResponse {

    private String email;
    private String name;
    private String phoneNumber;
    private Gender gender;
    private LocalDate birthday;

    public MemberResponse(Member member) {
        this.email = member.getEmail();
        this.name = member.getName();
        this.phoneNumber = member.getPhoneNumber();
        this.gender = member.getGender();
        this.birthday = member.getBirthDay();
    }
}