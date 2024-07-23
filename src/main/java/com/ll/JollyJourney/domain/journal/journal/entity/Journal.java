package com.ll.JollyJourney.domain.journal.journal.entity;

import com.ll.JollyJourney.global.jpa.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Journal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long journalId;

    private String content;

    @NotNull
    @Column(name = "modify_date")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    private Long likesCount;

    private boolean likesState;

    public void updateSocial(String content) {
        this.content = content;
    }

    public void updateLikesInfo(Long likesCount, boolean likesState) {
        this.likesCount = likesCount;
        this.likesState = likesState;
        this.modifyDate = LocalDateTime.now(); // 좋아요 정보를 업데이트했으므로 수정 날짜도 업데이트
    }
}

