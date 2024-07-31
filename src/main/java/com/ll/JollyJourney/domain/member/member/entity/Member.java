package com.ll.JollyJourney.domain.member.member.entity;

import com.ll.JollyJourney.domain.customer.answer.entity.Answer;
import com.ll.JollyJourney.domain.customer.question.entity.Question;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.enums.LoginType;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Entity
//@Getter
//@Builder
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor(access = AccessLevel.PROTECTED)
//public class Member extends BaseEntity implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    private String email;
//    private String password;
//    private String name;
//    private String phoneNumber;
//    private LocalDate birthDay;
//
//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//
//    @Enumerated(value = EnumType.STRING)
//    private MemberRole role;
//
//    @Enumerated(EnumType.STRING)
//    private LoginType loginType;
//
//    private String providerId;
//
//
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if (this.role.equals(MemberRole.ADMIN)) {
//            authorities.add(new SimpleGrantedAuthority("ADMIN"));
//        } else {
//            authorities.add(new SimpleGrantedAuthority("MEMBER"));
//        }
//
//        return authorities;
//    }
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Answer> answers = new ArrayList<>();
//
//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Question> questions = new ArrayList<>();
//
//
//    public void changePassword(String password) {
//        this.password = password;
//    }
//
//    public void modifyProfile(String encodedPassword) {
//        this.password = encodedPassword;
//    }
//
//
//    public void modifyProfile(String name, String phoneNumber, Gender gender, LocalDate birthDay) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        this.gender = gender;
//        this.birthDay = birthDay;
//    }
//
//    public void clearPw(String encodedPassword) {
//        this.password = encodedPassword;
//    }
//
//}
//
@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private LocalDate birthDay;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(value = EnumType.STRING)
    private MemberRole role;

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String providerId;

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