package com.ll.JollyJourney.domain.journal.service;

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

    @Transactional(readOnly=true)
    public List<JournalRes> getAllJournals() {
        List<Journal> journals = journalRepository.findAll();
        return journals.stream()
                .map(JournalRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Journal getJournal(Long journalId) {
        return journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
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