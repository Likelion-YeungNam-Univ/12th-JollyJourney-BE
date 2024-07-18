package com.ll.JollyJourney.domain.diary.diary.babydiary.entity;

import com.ll.JollyJourney.domain.baby.entity.Baby;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BabyDiary extends BaseEntity {

    @Comment("내용")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "baby_id")
    private Baby baby;

    @Comment("회원 권한")
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private MemberRole role; // 링크 받아서 연결하는 방식으로 할껀데 이렇게 권한을 줘야되는건가??

//    @Comment("아이 일기 목록")
//    @Builder.Default
//    @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL)
//    List<BabyDiaryL>  = new ArrayList<>(); // 일기 목록도 리스트로 짜야하나 의문,,,

}