package com.ll.JollyJourney.domain.journalcomment.service;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.repository.JournalRepository;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoReq;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoRes;
import com.ll.JollyJourney.domain.journalcomment.repository.JournalCommentRepository;
import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JournalCommentService {
    @Autowired
    private JournalCommentRepository journalCommentRepository;
    @Autowired
    private JournalRepository journalRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Transactional(readOnly=true)
    public List<JournalCoRes> getAllComments() {
        List<JournalComment> comments = journalCommentRepository.findAll();
        return comments.stream()
                .map(JournalCoRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public JournalCoRes getComment(Long commentId) {
        JournalComment journalComment = journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글 없음"));
        return JournalCoRes.fromEntity(journalComment);
    }

    @Transactional
    public JournalComment createComment(JournalCoReq request) {
        Journal journal = journalRepository.findById(request.journalId())
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
        Member member = memberRepository.findById(request.userId())
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없음"));
        JournalComment journalComment = request.toEntity(journal, member);
        return journalCommentRepository.save(journalComment);
    }

    @Transactional
    public JournalComment updateComment(Long commentId, JournalCoReq request) {
        JournalComment journalComment= journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));
        journalComment.setContent(request.content());

        return journalComment;
    }

    @Transactional
    public void deleteComment(Long commentId) {
        journalCommentRepository.deleteById(commentId);
    }

}


/*    public boolean isAuthor(Long commentId) {
        JournalComment comment = journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + commentId));
        String currentUsername = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return comment.getMember().getEmail().equals(currentUsername);
    }

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

 */