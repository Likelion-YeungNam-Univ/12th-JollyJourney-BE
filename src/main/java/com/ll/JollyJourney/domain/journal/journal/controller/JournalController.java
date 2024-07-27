package com.ll.JollyJourney.domain.journal.journal.controller;

import com.ll.JollyJourney.domain.journal.journal.dto.JournalFormDto;
import com.ll.JollyJourney.domain.journal.journal.entity.Journal;
import com.ll.JollyJourney.domain.journal.journal.service.JournalService;
import com.ll.JollyJourney.domain.journal.journalcomment.entity.JournalComment;
import com.ll.JollyJourney.domain.journal.journalcomment.service.JournalCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Page;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

import java.util.List;

@Controller
@RequestMapping("/journal")
@RequiredArgsConstructor
public class JournalController {
    private final JournalService journalService;
    private final JournalCommentService journalCommentService;

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
}