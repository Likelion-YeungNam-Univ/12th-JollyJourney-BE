package com.ll.JollyJourney.domain.journalcomment.repository;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JournalCommentRepository extends JpaRepository<JournalComment, Long> {
    Optional<JournalComment> findByJournal(Journal journal);
}