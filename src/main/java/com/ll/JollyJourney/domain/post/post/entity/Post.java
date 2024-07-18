package com.ll.JollyJourney.domain.post.post.entity;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;


    @NotNull
    @Column(name = "modify_date")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    private Long likesCount;

    private boolean likesState;

    @Comment("글 카테고리")
    @Enumerated(value = EnumType.STRING)
    private PostCategory postCategory;

    public void updatePost(String content) {
        this.content = content;
    }

    public void updateLikesInfo(Long likesCount, boolean likesState) {
        this.likesCount = likesCount;
        this.likesState = likesState;
        this.modifyDate = LocalDateTime.now(); // 좋아요 정보를 업데이트했으므로 수정 날짜도 업데이트
    }

}
