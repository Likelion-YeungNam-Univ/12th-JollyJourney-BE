package com.ll.JollyJourney.domain.member.member.entity;

import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {
    @Comment("회원 메일")
    @NotNull
    @Column(unique = true)
    private String email;

    @Comment("회원 비밀번호")
    @NotNull
    private String password;

    @Comment("회원 본명")
    @NotNull
    private String name;

    @Comment("회원 별명")
    @NotNull
    @Column(unique = true)
    private String nickname;

    @Comment("회원 연락처")
    @NotNull
    @Column(unique = true)
    private Long phoneNumber;

    @Comment("회원 생년월일")
    @NotNull
    private LocalDate birthday;

    @Comment("성별")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Comment("회원 권한")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

}
