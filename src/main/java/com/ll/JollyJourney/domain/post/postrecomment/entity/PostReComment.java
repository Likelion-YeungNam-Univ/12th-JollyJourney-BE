package com.ll.JollyJourney.domain.post.postrecomment.entity;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.post.postcomment.entity.PostComment;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostReComment extends BaseEntity {


    @ManyToOne(fetch = FetchType.LAZY)
    private PostComment postComment;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String reComment;
    private int likes;
}
