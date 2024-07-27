package com.ll.JollyJourney.domain.journal.journal.service;

import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.journal.repository.JournalRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.ll.JollyJourney.DataNotFoundException;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
@RequiredArgsConstructor
@Service
public class JournalService {
    private final JournalRepository journalRepository;

    public List<Journal> getList() {
        return this.journalRepository.findAll();
    }

    public Journal getJournal(Long id) {
        Optional<Journal> journal = this.journalRepository.findById(id);
        if (journal.isPresent()) {
            return journal.get();
        } else {
            throw new DataNotFoundException("journal not found");
        }
    }
    public void create(String title, String content) {
        Journal journal = Journal.builder()
                .title(title)
                .content(content)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .likesCount(0)
                .build();
        journalRepository.save(journal);
    }


    @Transactional
    public void modify(Long journalId, String title, String content) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new DataNotFoundException("Journal not found"));

        journal.updateJournal(title, content);

    }

    public void delete(Journal journal) {
        this.journalRepository.delete(journal);
    }
    public Page<Journal> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
        return this.journalRepository.findAll(pageable);
    }

    public Page<Journal> search(String type, String keyword, int page) {
        String searchKeyword = "%" + keyword + "%";
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 3, Sort.by(sorts));
        return this.journalRepository.searchByTypeAndKeyword(type, keyword, pageable);
    }

}