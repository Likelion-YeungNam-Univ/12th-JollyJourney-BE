package com.ll.JollyJourney.domain.likes.service;

import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.repository.JournalRepository;
import com.ll.JollyJourney.domain.likes.dto.LikesReq;
import com.ll.JollyJourney.domain.likes.entity.Likes;
import com.ll.JollyJourney.domain.likes.repository.LikesRepository;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final JournalRepository journalRepository;

    @Transactional
    public Likes addLikes(LikesReq likesReq) {
        Journal journal = journalRepository.findById(likesReq.journalId())
                .orElseThrow(() -> new IllegalArgumentException("Could not found journal id: " + likesReq.journalId()));
        Member member = memberRepository.findById(likesReq.memberId())
                .orElseThrow(() -> new IllegalArgumentException("Could not found member id: " + likesReq.memberId()));

        Likes likes = likesReq.toEntity(journal, member);
        return likesRepository.save(likes);
    }

    @Transactional
    public void deleteLikes(Long likesId) {
        likesRepository.deleteById(likesId);
    }
}

    /*
    @Transactional
    public Integer likeJournal(Long journalId, Long memberId) {
        Journal journal = journalRepository.findById(journalId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found journal id: " + journalId));

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Could not found member id: " + memberId));

        if (likesRepository.existsByJournalAndMember(journal, member)) {
            // 좋아요가 이미 존재하는 경우 삭제
            likesRepository.deleteByJournalAndMember(journal, member);
            journal.setLikesCount(journal.getLikesCount() - 1);
        } else {
            // 좋아요가 존재하지 않는 경우 추가
            Likes like = Likes.builder()
                    .journal(journal)
                    .member(member)
                    .build();
            likesRepository.save(like);
            journal.setLikesCount(journal.getLikesCount() + 1);
        }

        journalRepository.save(journal);
        return journal.getLikesCount();

     */

