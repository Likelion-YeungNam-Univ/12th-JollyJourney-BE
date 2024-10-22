package com.ll.JollyJourney.domain.likes.repository;

import com.ll.JollyJourney.domain.likes.entity.Likes;
import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByJournalAndMember(Journal journal, Member member);
}