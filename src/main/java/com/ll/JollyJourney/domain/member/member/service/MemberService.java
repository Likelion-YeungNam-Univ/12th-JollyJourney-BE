package com.ll.JollyJourney.domain.member.member.service;

import com.ll.JollyJourney.domain.member.member.dto.JoinRequest;
import com.ll.JollyJourney.domain.member.member.dto.ModifyRequest;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.enums.LoginType;
import com.ll.JollyJourney.global.security.config.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final OAuth2Service oAuth2Service;

    public void join(JoinRequest joinRequest) {
        validateEmail(joinRequest.getEmail());
        validateConfirmPassword(joinRequest.getPassword(), joinRequest.getPasswordConfirm());
        validatePhoneNumber(joinRequest.getPhoneNumber());

        Member member = joinRequest.toEntity(joinRequest);
        member.changePassword(passwordEncoder.encode(joinRequest.getPassword()));
        memberRepository.save(member);
    }

    public void validateEmail(String email) {
        Optional<Member> _findMember = findByEmail(email);
        if (_findMember.isPresent()) {
            Member findMember = _findMember.get();
            if (findMember.getLoginType().equals(LoginType.KAKAO)) {
                throw new IllegalStateException("카카오 로그인 계정입니다.");
            }
            if (findMember.getLoginType().equals(LoginType.APP)) {
                throw new IllegalStateException("이미 회원가입한 이메일입니다.");
            }
        }
    }

    public void validateConfirmPassword(String password, String passwordConfirm) {
        if (!confirmPassword(password, passwordConfirm)) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    public void validatePhoneNumber(String phoneNumber) {
        if (existsByPhoneNumber(phoneNumber)) {
            throw new IllegalStateException("중복된 전화번호입니다.");
        }
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }
    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
    public boolean existsByPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean confirmPassword(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    public Member getMember(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));
    }

    public boolean existsByEmailAndLoginType(String kakaoEmail, LoginType loginType) {
        return memberRepository.existsByEmailAndLoginType(kakaoEmail, loginType);
    }

    public Member saveMember(Member member) {
        Member saveMember = memberRepository.save(member);
        return saveMember;
    }

    public void modify(ModifyRequest modifyRequest, Member member) {

        //회원정보 수정에서 비밀번호 변경
        if (StringUtils.hasText(modifyRequest.getPassword())) { //변경하고자 하는 비밀번호가 입력되어있을때
            member.modifyProfile(passwordEncoder.encode(modifyRequest.getPassword()));
        }

        //비밀번호 제외 회원 정보 수정
        member.modifyProfile(modifyRequest.getName(), modifyRequest.getPhoneNumber(), modifyRequest.getGender(), modifyRequest.getBirthday());
        memberRepository.save(member);
    }

    public void revokeMember(SecurityUser securityUser) {
        Member member = securityUser.getMember();

        if (member.getLoginType().equals(LoginType.KAKAO)) {
            System.out.println("member.getProviderId() = " + member.getProviderId());
            oAuth2Service.kakaoRevoke(member.getProviderId());
        }

        memberRepository.delete(member);

    }
}
