package com.ll.JollyJourney.domain.member.member.service;

import com.ll.JollyJourney.domain.member.member.dto.JoinRequest;
import com.ll.JollyJourney.domain.member.member.dto.MemberResponse;
import com.ll.JollyJourney.domain.member.member.dto.ModifyRequest;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.enums.LoginType;
import com.ll.JollyJourney.global.security.config.SecurityUser;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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

    public MemberResponse getMemberResponse(String email) {
        Member member = getMember(email);
        return new MemberResponse(member);
    }

    public boolean existsByEmailAndLoginType(String kakaoEmail, LoginType loginType) {
        return memberRepository.existsByEmailAndLoginType(kakaoEmail, loginType);
    }

    public Member saveMember(Member member) {
        return memberRepository.save(member);
    }

    public void modify(ModifyRequest modifyRequest, Member member) {

        if (StringUtils.hasText(modifyRequest.getPassword())) {
            member.modifyProfile(passwordEncoder.encode(modifyRequest.getPassword()));
        }

        member.modifyProfile(modifyRequest.getName(), modifyRequest.getPhoneNumber(), modifyRequest.getGender(), modifyRequest.getBirthday());
        memberRepository.save(member);
    }

    public void revokeMember(SecurityUser securityUser) {
        Member member = securityUser.getMember();

        if (member.getLoginType().equals(LoginType.KAKAO)) {
            oAuth2Service.kakaoRevoke(member.getProviderId());
        }

        memberRepository.delete(member);
    }

    public Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 계정입니다."));
    }
}
// 이거 아님
//@Transactional
//@Service
//@RequiredArgsConstructor
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    // 회원가입
//    public void registerMember(MemberDto memberDto) {
//        validateEmail(memberDto.getEmail());
//        validatePhoneNumber(memberDto.getPhoneNumber());
//        validatePassword(memberDto.getPassword(), memberDto.getConfirmPassword());
//
//        Member member = Member.builder()
//                .email(memberDto.getEmail())
//                .password(passwordEncoder.encode(memberDto.getPassword()))
//                .name(memberDto.getName())
//                .phoneNumber(memberDto.getPhoneNumber())
//                .birthDay(memberDto.getBirthDay())
//                .gender(memberDto.getGender())
//                .loginType(memberDto.getLoginType())
//                .providerId(memberDto.getProviderId())
//                .role(MemberRole.MEMBER) // 기본 역할 설정
//                .build();
//
//        memberRepository.save(member);
//    }
//
//    // 이메일 유효성 검사
//    private void validateEmail(String email) {
//        if (memberRepository.findByEmail(email).isPresent()) {
//            throw new IllegalArgumentException("이미 등록된 이메일입니다.");
//        }
//    }
//
//    // 비밀번호 확인
//    private void validatePassword(String password, String confirmPassword) {
//        if (!password.equals(confirmPassword)) {
//            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
//        }
//    }
//
//    // 전화번호 중복 검사
//    private void validatePhoneNumber(String phoneNumber) {
//        if (memberRepository.findByPhoneNumber(phoneNumber).isPresent()) {
//            throw new IllegalArgumentException("이미 등록된 전화번호입니다.");
//        }
//    }
//
//    // 이메일로 회원 조회
//    public Member findByEmail(String email) {
//        return memberRepository.findByEmail(email)
//                .orElseThrow(() -> new IllegalArgumentException("이메일로 찾을 수 없습니다."));
//    }
//
//
//    // 회원 정보 수정
//    public void updateMember(Long userId, MemberDto memberDto) {
//        Member member = memberRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
//
//        if (memberDto.getPassword() != null) {
//            member.setPassword(passwordEncoder.encode(memberDto.getPassword()));
//        }
//        if (memberDto.getName() != null) {
//            member.setName(memberDto.getName());
//        }
//        if (memberDto.getPhoneNumber() != null) {
//            validatePhoneNumber(memberDto.getPhoneNumber());
//            member.setPhoneNumber(memberDto.getPhoneNumber());
//        }
//        if (memberDto.getGender() != null) {
//            member.setGender(memberDto.getGender());
//        }
//        if (memberDto.getBirthDay() != null) {
//            member.setBirthDay(memberDto.getBirthDay());
//        }
//
//        memberRepository.save(member);
//    }
//
//    // 회원 탈퇴
//    public void deleteMember(Long userId) {
//        Member member = memberRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("회원이 존재하지 않습니다."));
//
//        memberRepository.delete(member);
//    }
//}
