package com.ll.JollyJourney.domain.journal.journal.entity;

import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;


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

    /* 댓글 기능 추가
    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalComment> comments;
     */

    // 생성자 추가
    @Builder
    public Journal(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void updateJournal(String title, String content) {
        this.title = title;
        this.content = content;
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

    /*
    public List<JournalComment> getComments() {
        return comments;
    }
    
     */
}

