package com.ll.JollyJourney.domain.post.postcomment.entity;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.post.post.entity.Post;
import com.ll.JollyJourney.domain.post.postrecomment.entity.PostReComment;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComment extends BaseEntity {
    @JoinColumn(name = "post_Id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_Id", nullable = false)
    private Member member;

    @Column(length = 100)
    private String content;

    @OneToMany(mappedBy = "postComment", cascade = CascadeType.ALL)
    private List<PostReComment> postReComments = new ArrayList<>();
}
