package com.ll.JollyJourney.domain.journal.entity;

import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Journal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalId;

    @NotNull
    private String title; // 제목 필드 추가

    private String content;
    private int likesCount = 0;

    private String imageUrl;

    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalComment> comments;

    // 생성자 추가
    @Builder
    public Journal(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
    }

    public void updateJournal(String title, String content, String imageUrl) {
        this.title = title;
        this.content = content;
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }

    public Long getJournalId() {
        return journalId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public List<JournalComment> getComments() {
        return comments;
    }
    public void setComments(List<JournalComment> comments) {
        this.comments = comments;
    }

    public int getCommentCount() {
        return (comments == null) ? 0 : comments.size();
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

