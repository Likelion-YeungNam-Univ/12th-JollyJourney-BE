package com.ll.JollyJourney.domain.diary.controller;

import com.ll.JollyJourney.domain.diary.dto.DiaryReq;
import com.ll.JollyJourney.domain.diary.dto.DiaryRes;
import com.ll.JollyJourney.domain.diary.entity.Diary;
import com.ll.JollyJourney.domain.diary.service.DiaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/diary")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class DiaryController {
    private final DiaryService diaryService;

    @GetMapping("")
    public ResponseEntity<?> getAlldiaries(){
        List<DiaryRes> diaries = diaryService.getAlldiaries();
        return ResponseEntity.ok(diaries);
    }

    @GetMapping("/{userDId}")
    public ResponseEntity<DiaryRes> getDiary(@PathVariable Long userDId) {
        DiaryRes diaryRes = diaryService.getDiary(userDId);
        return ResponseEntity.ok(diaryRes);
    }

    @PostMapping("/create")
    public ResponseEntity<Diary> createDiary(@RequestBody DiaryReq request, Authentication authentication) {
        Diary diary = diaryService.createDiary(request, authentication);
        return ResponseEntity.ok(diary);
    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<DiaryRes> updateDiary(@PathVariable Long id, @RequestBody DiaryReq diaryReq, Authentication authentication) {
        Diary diary = diaryService.updateDiary(id, diaryReq, authentication);
        DiaryRes diaryRes = DiaryRes.fromEntity(diary);
        return ResponseEntity.ok(diaryRes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long id, Authentication authentication) {
        try {
            diaryService.deleteDiary(id, authentication);
            return ResponseEntity.ok("일기 삭제 완료");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }
}