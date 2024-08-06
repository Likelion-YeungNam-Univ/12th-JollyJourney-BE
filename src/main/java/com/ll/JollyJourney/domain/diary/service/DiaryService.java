package com.ll.JollyJourney.domain.diary.service;

import com.ll.JollyJourney.domain.diary.dto.DiaryReq;
import com.ll.JollyJourney.domain.diary.dto.DiaryRes;
import com.ll.JollyJourney.domain.diary.entity.Diary;
import com.ll.JollyJourney.domain.diary.repository.DiaryRepository;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.security.userdetails.CustomUserDetails;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryService {

    @Autowired
    private final DiaryRepository diaryRepository;

    @Autowired
    private final MemberRepository memberRepository;

    @Transactional
    public List<DiaryRes> getAlldiaries() {
        List<Diary> diaries = diaryRepository.findAll();
        return diaries.stream()
                .map(DiaryRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<DiaryRes> getAlldiaries(Long userDId) {
        List<Diary> diaries = diaryRepository.findByUserDId(userDId);
        return diaries.stream()
                .map(DiaryRes::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public DiaryRes getDiary(Long userDId) {
        Diary diary = diaryRepository.findById(userDId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록 없음"));
        return DiaryRes.fromEntity(diary);
    }

    @Transactional
    public Diary createDiary(DiaryReq request, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long currentUserId = userDetails.getMemberId();

        Member member = memberRepository.findById(currentUserId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없음"));
        Diary diary = request.toEntity(member);

        return diaryRepository.save(diary);
    }

    @Transactional
    public Diary updateDiary(Long userDId, DiaryReq request, Authentication authentication) {
        Diary diary = diaryRepository.findById(userDId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록 없음"));
        diary.update(request);
        return diaryRepository.save(diary);
    }

    @Transactional
    public void deleteDiary(Long userDId, Authentication authentication) {
        Diary diary = diaryRepository.findById(userDId)
                .orElseThrow(() -> new IllegalArgumentException("해당 기록 없음"));
        diaryRepository.deleteById(userDId);
    }

    // 특정 날짜의 일기 조회
    @Transactional
    public List<DiaryRes> getDiariesByUserDate(LocalDate userDate) {
        List<Diary> diaries = diaryRepository.findByUserDate(userDate);
        return diaries.stream()
                .map(DiaryRes::fromEntity)
                .collect(Collectors.toList());
    }
}



//@Service
//@Slf4j
//@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
//public class DiaryService {
//
//    @Autowired
//    private final DiaryRepository diaryRepository;
//
//    @Autowired
//    private final MemberRepository memberRepository;
//
//    @Transactional
//    public List<DiaryRes> getAlldiaries() {
//        List<Diary> diaries = diaryRepository.findAll();
//        return diaries.stream()
//                .map(DiaryRes::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//
//    @Transactional
//    public List<DiaryRes> getAlldiaries(Long userDId) {
//        List<Diary> diaries = diaryRepository.findByUserDId(userDId);
//        return diaries.stream()
//                .map(DiaryRes::fromEntity)
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public DiaryRes getDiary(Long userDId) {
//        Diary diary = diaryRepository.findById(userDId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 기록 없음"));
//        return DiaryRes.fromEntity(diary);
//    }
//
//    @Transactional
//    public Diary createDiary(DiaryReq request, Authentication authentication) {
//
//        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
//        Long currentUserId = userDetails.getMemberId();
//
//        Member member = memberRepository.findById(currentUserId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 회원 없음"));
//        Diary diary = request.toEntity(member);
//
//        return diaryRepository.save(diary);
//    }
//
//    @Transactional
//    public Diary updateDiary(Long userDId, DiaryReq request, Authentication authentication) {
//        Diary diary = diaryRepository.findById(userDId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 기록 없음"));
//        diary.update(request);
//        return diaryRepository.save(diary);
//    }
//
//    @Transactional
//    public void deleteDiary(Long userDId, Authentication authentication) {
//        Diary diary = diaryRepository.findById(userDId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 기록 없음"));
//        diaryRepository.deleteById(userDId);
//    }
//}
//
