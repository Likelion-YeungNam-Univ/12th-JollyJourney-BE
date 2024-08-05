package com.ll.JollyJourney.domain.journalcomment.service;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.repository.JournalRepository;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoReq;
import com.ll.JollyJourney.domain.journalcomment.dto.JournalCoRes;
import com.ll.JollyJourney.domain.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journalcomment.repository.JournalCommentRepository;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.security.userdetails.CustomUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    // 특정 journalId의 모든 댓글을 가져오는 메서드 추가
    @Transactional(readOnly = true)
    public List<JournalCoRes> getAllComments(Long journalId) {
        List<JournalComment> comments = journalCommentRepository.findByJournal_JournalId(journalId);
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
    public JournalComment createComment(JournalCoReq request, Authentication authentication) {
        Journal journal = journalRepository.findById(request.journalId())
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMemberId();

        Member member = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없음"));
        JournalComment journalComment = request.toEntity(journal, member);

        return journalCommentRepository.save(journalComment);
    }

    @Transactional
    public JournalComment updateComment(Long commentId, JournalCoReq request, Authentication authentication) {
        JournalComment journalComment = journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 정보글 없음"));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMemberId();

        if (!journalComment.getMember().getUserId().equals(currentUserId)) {
            throw new AccessDeniedException("댓글 작성자만 수정할 수 있습니다.");
        }

        journalComment.setContent(request.content());
        return journalComment;
    }

    @Transactional
    public void deleteComment(Long commentId, Authentication authentication) {
        JournalComment journalComment = journalCommentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글 없음"));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMemberId();
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"));

        if (!journalComment.getMember().getUserId().equals(currentUserId) && !isAdmin) {
            throw new AccessDeniedException("댓글 작성자 또는 관리자만 삭제할 수 있습니다.");
        }
        journalCommentRepository.deleteById(commentId);
    }
}