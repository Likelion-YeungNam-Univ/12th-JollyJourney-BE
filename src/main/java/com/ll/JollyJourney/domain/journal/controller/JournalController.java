package com.ll.JollyJourney.domain.journal.controller;

import com.ll.JollyJourney.domain.journal.dto.JournalReq;
import com.ll.JollyJourney.domain.journal.dto.JournalRes;
import com.ll.JollyJourney.domain.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.service.JournalService;
import com.ll.JollyJourney.domain.journalcomment.service.JournalCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/journals")
@RequiredArgsConstructor
// @Tag(name = "[정보글 관련 API]", description = "정보글 관련 CRUD API")
public class JournalController {
    private final JournalService journalService;
    private final JournalCommentService journalCommentService;

    @GetMapping("")
    public ResponseEntity<?> getAllJournals(){
        List<JournalRes> journals = journalService.getAllJournals();
        return ResponseEntity.ok(journals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JournalRes> getJournal(@PathVariable Long id) {
        Journal journal = journalService.getJournal(id);
        JournalRes journalRes = JournalRes.fromEntity(journal);
        return ResponseEntity.ok(journalRes);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<JournalRes> createJournal(@RequestBody JournalReq journalReq){
        Journal journal = journalService.createJournal(journalReq);
        JournalRes journalRes= JournalRes.fromEntity(journal);
        return ResponseEntity.ok(journalRes);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("modify/{id}")
    public ResponseEntity<JournalRes> updateJournal(@PathVariable Long id, @RequestBody JournalReq journalReq){
        Journal journal = journalService.updateJournal(id, journalReq);
        JournalRes journalRes = JournalRes.fromEntity(journal);
        return ResponseEntity.ok(journalRes);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<JournalRes> deleteJournal(@PathVariable Long id){
        journalService.deleteJournal(id);
        return ResponseEntity.ok().build();
    }
}


/*

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "type", required = false) String type,
                       @RequestParam(value = "keyword", required = false) String keyword) {
        Page<Journal> paging;
        if (type != null && keyword != null && !keyword.isEmpty()) {
            paging = journalService.search(type, keyword, page);
        } else {
            paging = journalService.getList(page);
        }
        model.addAttribute("paging", paging);
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        return "domain/journal/journalList";
    }

    @GetMapping("/detail/{journalId}")
    public String detail(Model model, @PathVariable("journalId") Long journalId) {
        Journal journal = journalService.getJournal(journalId);
        List<JournalComment> comments = journalCommentService.getCommentsByJournalId(journalId);
        model.addAttribute("journal", journal);
        model.addAttribute("comments", comments);
        return "domain/journal/journalDetail";
    }

    @GetMapping("/create")
    public String journalCreate(Model model) {
        model.addAttribute("journalForm", new JournalFormDto());
        return "domain/journal/journalForm";
    }

    @PostMapping("/create")
    public String journalCreate(@Valid @ModelAttribute("journalForm") JournalFormDto journalFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/journal/journalForm";
        }
        journalService.create(journalFormDto.getTitle(), journalFormDto.getContent());
        return "redirect:/journal/list";
    }

    @GetMapping("/modify/{journalId}")
    public String journalModify(@PathVariable("journalId") Long journalId, Model model) {
        Journal journal = journalService.getJournal(journalId);
        JournalFormDto journalFormDto = new JournalFormDto(journal.getJournalId(), journal.getTitle(), journal.getContent());
        model.addAttribute("journalForm", journalFormDto);
        return "domain/journal/journalForm";
    }

    @PostMapping("/modify/{journalId}")
    public String journalModify(@PathVariable("journalId") Long journalId, @Valid @ModelAttribute("journalForm") JournalFormDto journalFormDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "domain/journal/journalForm";
        }
        journalService.modify(journalId, journalFormDto.getTitle(), journalFormDto.getContent());
        return "redirect:/journal/detail/" + journalId;
    }

    @GetMapping("/delete/{journalId}")
    public String journalDelete(@PathVariable("journalId") Long journalId) {
        Journal journal = journalService.getJournal(journalId);
        journalService.delete(journal);
        return "redirect:/journal/list";
    }
 */