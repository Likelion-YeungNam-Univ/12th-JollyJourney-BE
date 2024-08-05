package com.ll.JollyJourney.domain.likes.service;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.repository.JournalRepository;
import com.ll.JollyJourney.domain.likes.dto.LikesReq;
import com.ll.JollyJourney.domain.likes.dto.LikesRes;
import com.ll.JollyJourney.domain.likes.entity.Likes;
import com.ll.JollyJourney.domain.likes.repository.LikesRepository;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final JournalRepository journalRepository;

    @Transactional
    public LikesRes toggleLikes(LikesReq likesReq, Authentication authentication) {
        Journal journal = journalRepository.findById(likesReq.journalId())
                .orElseThrow(() -> new IllegalArgumentException("해당 저널 없음: " + likesReq.journalId()));

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMemberId();
        Member member = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없음: " + currentUserId));

        Likes existingLike = likesRepository.findByJournalAndMember(journal, member);
        if (existingLike != null) {
            likesRepository.delete(existingLike);
            if (journal.getLikesCount() > 0) {
                journal.setLikesCount(journal.getLikesCount() - 1);
            }
            journalRepository.save(journal);
            return LikesRes.fromEntity(existingLike, "좋아요가 취소되었습니다.", journal.getLikesCount());
        } else {
            Likes newLike = Likes.builder()
                    .journal(journal)
                    .member(member)
                    .build();
            journal.setLikesCount(journal.getLikesCount() + 1);
            journalRepository.save(journal);
            Likes savedLike = likesRepository.save(newLike);
            return LikesRes.fromEntity(savedLike, "좋아요가 추가되었습니다.", journal.getLikesCount());
        }
    }
}

