package com.ll.JollyJourney.domain.baby.entity;

import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Baby extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BabyId;

    @Comment("아이 이름")
    @NotNull
    private String babyName;

    @Comment("성별")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Comment("아이 생년월일")
    @NotNull
    private LocalDate birthday;

    @Comment("아이 키")
    @NotNull
    private Long height;

    @Comment("아이 몸무게")
    @NotNull
    private Long weight;

    @Comment("아이 머리둘레")
    @NotNull
    private Long headSize;

    @Comment("회원 권한")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MemberRole role; // 링크를 받은 사람이 작성할 수 있게 해야하는데 어떤식으로 해야할지 고민중,,,

}
