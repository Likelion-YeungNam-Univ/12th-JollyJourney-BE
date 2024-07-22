package com.ll.JollyJourney.domain.member.member.entity;

import com.ll.JollyJourney.domain.customer.answer.entity.Answer;
import com.ll.JollyJourney.domain.customer.question.entity.Question;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.enums.LoginType;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

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
    private String phoneNumber;

    @Comment("회원 생년월일")
    @NotNull
    private LocalDate birthDay;

    @Comment("성별")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Comment("회원 권한")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String providerId;

    @NotNull
    @CreatedDate
    private LocalDateTime createDate;

    @NotNull
    @LastModifiedDate
    private LocalDateTime modifyDate;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        if (this.role.equals(MemberRole.ADMIN)) {
            authorities.add(new SimpleGrantedAuthority("ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("MEMBER"));
        }

        return authorities;
    }

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Answer> answers = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();


    public void changePassword(String password) {
        this.password = password;
    }

    public void modifyProfile(String encodedPassword) {
        this.password = encodedPassword;
    }


    public void modifyProfile(String name, String phoneNumber, Gender gender, LocalDate birthDay) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthDay = birthDay;
    }

    public void clearPw(String encodedPassword) {
        this.password = encodedPassword;
    }

}

