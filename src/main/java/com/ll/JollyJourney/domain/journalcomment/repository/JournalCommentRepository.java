package com.ll.JollyJourney.domain.journalcomment.repository;

import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JournalCommentRepository extends JpaRepository<JournalComment, Long> {
    List<JournalComment> findByJournal_JournalId(Long journalId); // 특정 journalId의 모든 댓글을 반환하는 메서드
}