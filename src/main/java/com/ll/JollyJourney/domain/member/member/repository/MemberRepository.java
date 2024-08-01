package com.ll.JollyJourney.domain.member.member.repository;

import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.global.enums.LoginType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByEmailAndLoginType(String email, LoginType loginType);
    boolean existsByPhoneNumber(String phoneNumber);

    Optional<Object> findByPhoneNumber(String phoneNumber);
}
