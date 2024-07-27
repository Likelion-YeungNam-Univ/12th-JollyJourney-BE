package com.ll.JollyJourney.domain.likes.repository;

import com.ll.JollyJourney.domain.likes.entity.Likes;
import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    boolean existsByJournalAndMember(Journal journal, Member member);
    Optional<Likes> findByJournalAndMember(Journal journal, Member member);
    void deleteByJournalAndMember(Journal journal, Member member);
}