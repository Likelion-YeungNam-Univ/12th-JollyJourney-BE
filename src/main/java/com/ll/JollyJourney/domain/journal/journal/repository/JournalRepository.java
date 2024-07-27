package com.ll.JollyJourney.domain.journal.journal.repository;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JournalRepository extends JpaRepository<Journal, Long> {
    Journal findByTitle(String title);
    Journal findByTitleAndContent(String title, String content);
    List<Journal> findByTitleLike(String title);
    Page<Journal> findAll(Pageable pageable);

    @Query("SELECT j FROM Journal j WHERE (:type = 'title' AND j.title LIKE %:keyword%) OR (:type = 'content' AND j.content LIKE %:keyword%)")
    Page<Journal> searchByTypeAndKeyword(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);
}