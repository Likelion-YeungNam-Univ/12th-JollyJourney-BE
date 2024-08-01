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
    // private final MemberRepository memberRepository;

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
    public Journal createJournal(JournalReq request) {
        Journal journal = request.toEntity();
        journalRepository.save(journal);
        return journalRepository.save(journal);
    }

    @Transactional
    public Journal updateJournal(Long journalId, JournalReq request) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
        journal.updateJournal(request.title(), request.content());
        return journal;
    }

    @Transactional
    public void deleteJournal(Long journalId) {
        journalRepository.deleteById(journalId);
    }
}




/*
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

 */