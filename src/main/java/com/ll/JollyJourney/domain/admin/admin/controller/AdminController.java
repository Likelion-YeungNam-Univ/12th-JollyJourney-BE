package com.ll.JollyJourney.domain.admin.admin.controller;


import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    private MemberRepository memberRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/admin/members")
    public String getAdminMembers(Model model) {
        Iterable<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);
        return "admin/members"; // Thymeleaf 템플릿 파일 경로
    }


}