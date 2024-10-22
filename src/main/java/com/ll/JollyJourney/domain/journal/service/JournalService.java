package com.ll.JollyJourney.domain.journal.service;

import com.ll.JollyJourney.domain.journal.dto.JournalListRes;
import com.ll.JollyJourney.domain.journal.dto.JournalReq;
import com.ll.JollyJourney.domain.journal.dto.JournalRes;
import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.repository.JournalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalService {
    private final JournalRepository journalRepository;

    public List<JournalListRes> getAllJournals() {
        List<Journal> journals = journalRepository.findAll();
        return journals.stream()
                .map(JournalListRes::fromEntity)
                .collect(Collectors.toList());
    }
    @Transactional
    public JournalRes getJournal(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
        journal.incrementViewCount();
        journalRepository.save(journal);
        return JournalRes.fromEntity(journal);
    }
    
    @Transactional
    public Journal createJournal(JournalReq request, String imageUrl) {
        Journal journal = request.toEntity(imageUrl);
        return journalRepository.save(journal);
    }


    @Transactional
    public Journal updateJournal(Long journalId, JournalReq request, String imageUrl) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
        journal.updateJournal(request.title(), request.content(), imageUrl);
        return journal;
    }


    @Transactional
    public void deleteJournal(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
        journalRepository.delete(journal);
    }
}