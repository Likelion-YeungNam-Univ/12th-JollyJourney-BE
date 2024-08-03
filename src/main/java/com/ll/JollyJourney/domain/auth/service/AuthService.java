package com.ll.JollyJourney.domain.auth.service;

import com.ll.JollyJourney.domain.auth.dto.*;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.security.jwt.JwtTokenProvider;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthService {
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public TokenRes signIn(SignInReq request){
        Member member = checkMemberValid(request);

        return issueToken(member);
    }

    public Member signUp(SignUpReq request){
        // 1. 이 회원이 DB에 이미 존재하는지? (email을 가지고 중복검사)
        Member member = checkMemberAvailable(request);

        return member;
    }

    public Member modifyInfo(ModifyInfoReq request) {
        Member member = readMemberOrThrow(request.email());

        member.modifyProfile(
                request.newName() != null ? request.newName() : member.getName(),
                request.newPhoneNumber() != null ? request.newPhoneNumber() : member.getPhoneNumber(),
                request.newGender() != null ? request.newGender() : member.getGender(),
                request.newBirthDay() != null ? request.newBirthDay() : member.getBirthDay()
        );

        return memberRepository.save(member);
    }

    public Member changePassword(ChangePasswordReq request) {
        Member member = readMemberOrThrow(request.email());

        if (!member.getPassword().equals(request.oldPassword())) {
            log.error("기존 비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("기존 비밀번호가 일치하지 않습니다.");
        }

        member.changePassword(request.newPassword());
        return memberRepository.save(member);
    }



    private Member checkMemberValid(SignInReq request){
        Member member = readMemberOrThrow(request.email());

        if(!member.getPassword().equals(request.password())){
            log.error("비밀번호가 일치하지 않습니다.");
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }

    private Member readMemberOrThrow(String email){
        Member member = memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 계정입니다.")
        );

        return member;
    }

    private Member checkMemberAvailable(SignUpReq request){
        if(memberRepository.existsByEmail(request.getEmail())){
            log.error("이미 존재하는 계정입니다.");
            throw new IllegalArgumentException("이미 존재하는 계정입니다.");
        }
        log.info("{} 계정 생성",request.getEmail());
        return memberRepository.save(request.toEntity());
    }

    private TokenRes issueToken(Member member){
        return new TokenRes(
                jwtTokenProvider.issueAccessToken(member.getEmail(), member.getRole().name()),
                jwtTokenProvider.issueRefreshToken(member.getEmail()));
    }

}
