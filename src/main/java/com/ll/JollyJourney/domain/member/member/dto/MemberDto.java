package com.ll.JollyJourney.domain.member.member.dto;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.enums.LoginType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberDto {

    private Long userId;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private LocalDate birthDay;
    private Gender gender;
    private MemberRole role;
    private LoginType loginType;
    private String providerId;

    public MemberDto(Long userId, String email, String name, String password, String phoneNumber, LocalDate birthDay,Gender gender, MemberRole role, LoginType loginType, String providerId ) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
        this.gender = gender;
        this.role = role;
        this.loginType = loginType;
        this.providerId = providerId;
    }

    // fromEntity 메서드
    public static MemberDto fromEntity(Member member) {
        return new MemberDto(
                member.getUserId(),
                member.getEmail(),
                member.getPassword(),
                member.getName(),
                member.getPhoneNumber(),
                member.getBirthDay(),
                member.getGender(),
                member.getRole(),
                member.getLoginType(),
                member.getProviderId()
        );
    }
}
