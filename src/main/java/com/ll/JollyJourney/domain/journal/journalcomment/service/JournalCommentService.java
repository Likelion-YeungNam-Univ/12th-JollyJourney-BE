package com.ll.JollyJourney.domain.journal.journalcomment.service;

import com.ll.JollyJourney.DataNotFoundException;
import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.journal.repository.JournalRepository;
import com.ll.JollyJourney.domain.journal.journalcomment.dto.JournalCommentDto;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journal.journalcomment.repository.JournalCommentRepository;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class JournalCommentService {

    private static final Logger logger = LoggerFactory.getLogger(JournalCommentService.class);

    @Autowired
    private JournalCommentRepository journalCommentRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private MemberRepository memberRepository;


    public void addComment(JournalCommentDto journalCommentDto) {
        logger.info("Adding comment: journalId={}, memberId={}, comment={}", journalCommentDto.getJournalId(), journalCommentDto.getMemberId(), journalCommentDto.getComment());

        Journal journal = journalRepository.findById(journalCommentDto.getJournalId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid journal ID"));
        Member member = memberRepository.findById(journalCommentDto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        JournalComment comment = JournalComment.builder()
                .journal(journal)
                .member(member)
                .content(journalCommentDto.getComment())
                .build();

        journalCommentRepository.save(comment);
        logger.info("Comment saved successfully");
    }


    public void updateComment(Long commentId, String newContent) {
        JournalComment comment = journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));

        // 필드 업데이트 후 저장
        comment.setContent(newContent);
        journalCommentRepository.save(comment);
        logger.info("Comment updated successfully");
    }


    public void deleteComment(Long commentId) {
        journalCommentRepository.deleteById(commentId);
    }
    public List<JournalComment> getCommentsByJournalId(Long journalId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid journal ID"));
        return journalCommentRepository.findByJournal(journal);
    }

    public JournalComment getCommentById(Long commentId) {
        return journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment ID"));
    }

}