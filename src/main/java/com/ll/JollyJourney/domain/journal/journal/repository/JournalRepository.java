package com.ll.JollyJourney.domain.journal.journal.repository;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {
    Optional<Journal> findByTitle(String title);
    Optional<Journal> findByTitleAndContent(String title, String content);
    List<Journal> findByTitleLike(String title);
    Page<Journal> findAll(Pageable pageable);

    /*
    @PersistenceContext;
    EntityManager em;

    public void save(Journal journal) {
        em.persist(journal);
    }
    public Journal findjournal(Long id) {}
    public List<Journal> findAll() {}


    */

    @Query("SELECT j FROM Journal j WHERE (:type = 'title' AND j.title LIKE %:keyword%) OR (:type = 'content' AND j.content LIKE %:keyword%)")
    Page<Journal> searchByTypeAndKeyword(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);
}