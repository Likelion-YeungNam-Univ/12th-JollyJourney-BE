package com.ll.JollyJourney.domain.diary.controller;

import com.ll.JollyJourney.domain.diary.dto.DiaryReq;
import com.ll.JollyJourney.domain.diary.dto.DiaryRes;
import com.ll.JollyJourney.domain.diary.entity.Diary;
import com.ll.JollyJourney.domain.diary.service.DiaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@Tag(name = "[Diary 관련 API]", description = "나의 일기 관련 CRUD API")
public class DiaryController {
    private final DiaryService diaryService;

    @Operation(summary = "모든 일기 조회", description = "모든 일기를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "모든 일기 조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                    [
                        {
                            "userDId": 1,
                            "userDate": "2023-08-06",
                            "meditationReport": "명상 30분",
                            "thanksReport": "오늘 하루에 감사한 일 3가지",
                            "exerciseReport": "조깅 5km",
                            "exerciseTime": "00:45:00",
                            "sleepTime": "08:00:00",
                            "sleepQuality": "좋음",
                            "kidReport": "아이와 함께 놀기 2시간",
                            "bodyStatus": "HEADACHE",
                            "emotionStatus": "HAPPINESS"
                        }
                    ]
                    """))),
            @ApiResponse(responseCode = "400", description = "모든 일기 조회 실패")
    })
    @GetMapping("")
    public ResponseEntity<List<DiaryRes>> getAllDiaries(@PathVariable Long userDId) {
        List<DiaryRes> diaries = diaryService.getAlldiaries(userDId);
        return ResponseEntity.ok(diaries);
    }

    @Operation(summary = "특정 일기 조회", description = "userDId로 특정 일기를 조회하는 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "특정 일기 조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                                "userDId": 1,
                                "userDate": "2023-08-06",
                                "meditationReport": "명상 30분",
                                "thanksReport": "오늘 하루에 감사한 일 3가지",
                                "exerciseReport": "조깅 5km",
                                "exerciseTime": "00:45:00",
                                "sleepTime": "08:00:00",
                                "sleepQuality": "좋음",
                                "kidReport": "아이와 함께 놀기 2시간",
                                "bodyStatus": "HEADACHE",
                                "emotionStatus": "HAPPINESS"
                            }
                            """))),
            @ApiResponse(responseCode = "400", description = "특정 일기 조회 실패")
    })
    @GetMapping("/{userDId}")
    public ResponseEntity<DiaryRes> getDiary(@PathVariable Long userDId) {
        DiaryRes diaryRes = diaryService.getDiary(userDId);
        return ResponseEntity.ok(diaryRes);
    }

    @Operation(summary = "일기 생성", description = "새로운 일기를 생성합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일기 생성 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                                "userDId": 1,
                                "userDate": "2023-08-06",
                                "meditationReport": "명상 30분",
                                "thanksReport": "오늘 하루에 감사한 일 3가지",
                                "exerciseReport": "조깅 5km",
                                "exerciseTime": "00:45:00",
                                "sleepTime": "08:00:00",
                                "sleepQuality": "좋음",
                                "kidReport": "아이와 함께 놀기 2시간",
                                "bodyStatus": "HEADACHE",
                                "emotionStatus": "HAPPINESS"
                            }
                            """))),
            @ApiResponse(responseCode = "400", description = "일기 생성 실패")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @PostMapping("/create")
    public ResponseEntity<Diary> createDiary(@RequestBody DiaryReq request, Authentication authentication) {
        Diary diary = diaryService.createDiary(request, authentication);
        return ResponseEntity.ok(diary);
    }

    @Operation(summary = "일기 수정", description = "기존 일기를 수정합니다.",
            security = @SecurityRequirement(name = "BearerAuth"))
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일기 수정 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            {
                                "userDId": 1,
                                "userDate": "2023-08-06",
                                "meditationReport": "수정된 명상 기록",
                                "thanksReport": "수정된 감사 기록",
                                "exerciseReport": "수정된 운동 기록",
                                "exerciseTime": "01:00:00",
                                "sleepTime": "07:30:00",
                                "sleepQuality": "보통",
                                "kidReport": "수정된 육아 기록",
                                "bodyStatus": "HEADACHE",
                                "emotionStatus": "HAPPINESS"
                            }
                            """))),
            @ApiResponse(responseCode = "400", description = "일기 수정 실패")
    })
    @PutMapping("/modify/{id}")
    public ResponseEntity<DiaryRes> updateDiary(@PathVariable Long userDId, @PathVariable Long id, @RequestBody DiaryReq diaryReq, Authentication authentication) {
        Diary diary = diaryService.updateDiary(id, diaryReq, authentication);
        DiaryRes diaryRes = DiaryRes.fromEntity(diary);
        return ResponseEntity.ok(diaryRes);
    }

    @Operation(summary = "일기 삭제", description = "기존 일기를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "일기 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "일기 삭제 실패")
    })
    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDiary(@PathVariable Long id, Authentication authentication) {
        try {
            diaryService.deleteDiary(id, authentication);
            return ResponseEntity.ok("일기 삭제 완료");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }

    @Operation(summary = "특정 날짜의 일기 조회", description = "userDate로 특정 날짜의 일기를 조회하는 기능")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "특정 날짜의 일기 조회 성공",
                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
                            [
                                {
                                    "userDId": 1,
                                    "userDate": "2023-08-06",
                                    "meditationReport": "명상 30분",
                                    "thanksReport": "오늘 하루에 감사한 일 3가지",
                                    "exerciseReport": "조깅 5km",
                                    "exerciseTime": "00:45:00",
                                    "sleepTime": "08:00:00",
                                    "sleepQuality": "좋음",
                                    "kidReport": "아이와 함께 놀기 2시간",
                                    "bodyStatus": "HEADACHE",
                                    "emotionStatus": "HAPPINESS"
                                }
                            ]
                            """))),
            @ApiResponse(responseCode = "400", description = "특정 날짜의 일기 조회 실패")
    })
    @GetMapping("/date/{userDate}")
    public ResponseEntity<List<DiaryRes>> getDiariesByUserDate(@PathVariable String userDate) {
        LocalDate date = LocalDate.parse(userDate);
        List<DiaryRes> diaries = diaryService.getDiariesByUserDate(date);
        return ResponseEntity.ok(diaries);
    }
}

//@Slf4j
//@RestController
//@RequestMapping("/diary")
//@RequiredArgsConstructor
//@Tag(name = "[Diary 관련 API]", description = "나의 일기 관련 CRUD API")
//public class DiaryController {
//    private final DiaryService diaryService;
//
//    @Operation(summary = "모든 일기 조회", description = "모든 일기를 조회합니다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "모든 일기 조회 성공",
//                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
//                    [
//                        {
//                            "userDId": 1,
//                            "meditationReport": "명상 30분",
//                            "thanksReport": "오늘 하루에 감사한 일 3가지",
//                            "exerciseReport": "조깅 5km",
//                            "exerciseTime": "00:45:00",
//                            "sleepTime": "08:00:00",
//                            "sleepQuality": "좋음",
//                            "kidReport": "아이와 함께 놀기 2시간",
//                            "bodyStatus": "HEADACHE",
//                            "emotionStatus": "HAPPINESS"
//                        }
//                    ]
//                    """))),
//            @ApiResponse(responseCode = "400", description = "모든 일기 조회 실패")
//    })
//    @GetMapping("")
//    public ResponseEntity<List<DiaryRes>> getAllDiaries(@PathVariable Long userDId) {
//        List<DiaryRes> diaries = diaryService.getAlldiaries(userDId);
//        return ResponseEntity.ok(diaries);
//    }
//
//    @Operation(summary = "특정 일기 조회", description = "userDId로 특정 일기를 조회하는 기능")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "특정 일기 조회 성공",
//                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
//                            {
//                                "userDId": 1,
//                                "meditationReport": "명상 30분",
//                                "thanksReport": "오늘 하루에 감사한 일 3가지",
//                                "exerciseReport": "조깅 5km",
//                                "exerciseTime": "00:45:00",
//                                "sleepTime": "08:00:00",
//                                "sleepQuality": "좋음",
//                                "kidReport": "아이와 함께 놀기 2시간",
//                                "bodyStatus": "HEADACHE",
//                                "emotionStatus": "HAPPINESS"
//                            }
//                            """))),
//            @ApiResponse(responseCode = "400", description = "특정 일기 조회 실패")
//    })
//    @GetMapping("/{userDId}")
//    public ResponseEntity<DiaryRes> getDiary(@PathVariable Long userDId) {
//        DiaryRes diaryRes = diaryService.getDiary(userDId);
//        return ResponseEntity.ok(diaryRes);
//    }
//
//    @Operation(summary = "일기 생성", description = "새로운 일기를 생성합니다.",
//            security = @SecurityRequirement(name = "BearerAuth"))
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "일기 생성 성공",
//                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
//                            {
//                                "userDId": 1,
//                                "meditationReport": "명상 30분",
//                                "thanksReport": "오늘 하루에 감사한 일 3가지",
//                                "exerciseReport": "조깅 5km",
//                                "exerciseTime": "00:45:00",
//                                "sleepTime": "08:00:00",
//                                "sleepQuality": "좋음",
//                                "kidReport": "아이와 함께 놀기 2시간",
//                                "bodyStatus": "HEADACHE",
//                                "emotionStatus": "HAPPINESS"
//                            }
//                            """))),
//            @ApiResponse(responseCode = "400", description = "일기 생성 실패")
//    })
//
//    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
//    @PostMapping("/create")
//    public ResponseEntity<Diary> createDiary(@RequestBody DiaryReq request, Authentication authentication) {
//        Diary diary = diaryService.createDiary(request, authentication);
//        return ResponseEntity.ok(diary);
//    }
//
//    @Operation(summary = "일기 수정", description = "기존 일기를 수정합니다.",
//            security = @SecurityRequirement(name = "BearerAuth"))
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "일기 수정 성공",
//                    content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
//                            {
//                                "userDId": 1,
//                                "meditationReport": "수정된 명상 기록",
//                                "thanksReport": "수정된 감사 기록",
//                                "exerciseReport": "수정된 운동 기록",
//                                "exerciseTime": "01:00:00",
//                                "sleepTime": "07:30:00",
//                                "sleepQuality": "보통",
//                                "kidReport": "수정된 육아 기록",
//                                "bodyStatus": "HEADACHE",
//                                "emotionStatus": "HAPPINESS"
//                            }
//                            """))),
//            @ApiResponse(responseCode = "400", description = "일기 수정 실패")
//    })
//    @PutMapping("/modify/{id}")
//    public ResponseEntity<DiaryRes> updateDiary(@PathVariable Long userDId, @PathVariable Long id, @RequestBody DiaryReq diaryReq, Authentication authentication) {
//        Diary diary = diaryService.updateDiary(id, diaryReq, authentication);
//        DiaryRes diaryRes = DiaryRes.fromEntity(diary);
//        return ResponseEntity.ok(diaryRes);
//    }
//
//    @Operation(summary = "일기 삭제", description = "기존 일기를 삭제합니다.")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "일기 삭제 성공"),
//            @ApiResponse(responseCode = "400", description = "일기 삭제 실패")
//    })
//
//    @PreAuthorize("hasAnyAuthority('ROLE_MEMBER')")
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteDiary(@PathVariable Long id, Authentication authentication) {
//        try {
//            diaryService.deleteDiary(id, authentication);
//            return ResponseEntity.ok("일기 삭제 완료");
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.status(400).body(ex.getMessage());
//        }
//    }
//}






//@Slf4j
//@Controller
//@RequestMapping("/diary")
//@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
//public class DiaryController {
//    private final DiaryService diaryService;
//
//    @GetMapping("")
//    public ResponseEntity<?> getAlldiaries(){
//        List<DiaryRes> diaries = diaryService.getAlldiaries();
//        return ResponseEntity.ok(diaries);
//    }
//
//    @GetMapping("/{userDId}")
//    public ResponseEntity<DiaryRes> getDiary(@PathVariable Long userDId) {
//        DiaryRes diaryRes = diaryService.getDiary(userDId);
//        return ResponseEntity.ok(diaryRes);
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Diary> createDiary(@RequestBody DiaryReq request, Authentication authentication) {
//        Diary diary = diaryService.createDiary(request, authentication);
//        return ResponseEntity.ok(diary);
//    }
//
//    @PutMapping("/modify/{id}")
//    public ResponseEntity<DiaryRes> updateDiary(@PathVariable Long id, @RequestBody DiaryReq diaryReq, Authentication authentication) {
//        Diary diary = diaryService.updateDiary(id, diaryReq, authentication);
//        DiaryRes diaryRes = DiaryRes.fromEntity(diary);
//        return ResponseEntity.ok(diaryRes);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteDiary(@PathVariable Long id, Authentication authentication) {
//        try {
//            diaryService.deleteDiary(id, authentication);
//            return ResponseEntity.ok("일기 삭제 완료");
//        } catch (IllegalArgumentException ex) {
//            return ResponseEntity.status(400).body(ex.getMessage());
//        }
//    }
//}