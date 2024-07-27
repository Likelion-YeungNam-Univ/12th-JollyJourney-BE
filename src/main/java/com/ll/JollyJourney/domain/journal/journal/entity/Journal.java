package com.ll.JollyJourney.domain.journal.journal.entity;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
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

    @NotNull
    @CreationTimestamp
    @Column(name = "create_date", nullable = false, updatable = false)
    private LocalDateTime createDate;

    @NotNull
    @Column(name = "modify_date")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    private int likesCount = 0;

    // 댓글 기능 추가
    @OneToMany(mappedBy = "journal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<JournalComment> comments;

    public void updateJournal(String title, String content) {
        this.title = title;
        this.content = content;
        this.modifyDate = LocalDateTime.now();
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
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


}

