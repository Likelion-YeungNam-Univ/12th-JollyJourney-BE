package com.ll.JollyJourney.domain.admin.admin.service;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.enums.LoginType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public void createAdminAccount() {
        if (!memberRepository.existsByEmail(adminEmail)) {
            Member admin = Member.builder()
                    .email(adminEmail)
                    .password(passwordEncoder.encode(adminPassword))
                    .name("Admin")
                    .phoneNumber("000-0000-0000")
                    .birthDay(LocalDate.now())
                    .gender(Gender.FEMALE)
                    .role(MemberRole.ADMIN)
                    .loginType(LoginType.APP)
                    .build();
            memberRepository.save(admin);
            logger.info("Admin account created with email: " + adminEmail);
        } else {
            logger.info("Admin account already exists with email: " + adminEmail);
        }
    }
}