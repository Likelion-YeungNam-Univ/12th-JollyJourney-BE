package com.ll.JollyJourney.domain.journal.journalcomment.repository;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;



public interface JournalCommentRepository extends JpaRepository<JournalComment, Long> {
    List<JournalComment> findByJournal(Journal journal);
}