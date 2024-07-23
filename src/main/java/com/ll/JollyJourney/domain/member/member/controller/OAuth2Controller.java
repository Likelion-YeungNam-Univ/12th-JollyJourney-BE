package com.ll.JollyJourney.domain.member.member.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoInfo;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.service.OAuth2Service;
import com.ll.JollyJourney.global.enums.LoginType;
import com.ll.JollyJourney.global.security.config.SecurityUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuth2Controller {

    @Value("${kakao.client.id}")
    String clientId;
    @Value("${kakao.redirect.uri}")
    String redirectUri;
    @Value("${kakao.client.secret}")
    String clientSecret;

    private final OAuth2Service oAuth2Service;

    /**
     * 카카오 로그인 요청
     * @return
     */
    @GetMapping(value="/kakao")
    public String kakaoConnect() {
        StringBuffer url = new StringBuffer();
        url.append("https://kauth.kakao.com/oauth/authorize?");
        url.append("client_id="+clientId);
        url.append("&redirect_uri="+redirectUri);
        url.append("&response_type=code");
        return "redirect:" + url.toString();
    }

    /**
     * 카카오 로그인
     * @return
     */
    @GetMapping("/kakao/callback")
    public String kakaoCallback(String code, HttpSession session) {

        // SETP1 : 인가코드 받기
        // (카카오 인증 서버는 서비스 서버의 Redirect URI로 인가 코드를 전달합니다.)
        // System.out.println(code);

        // STEP2: 인가코드를 기반으로 토큰(Access Token) 발급
        String accessToken = null;
        try {
            accessToken = oAuth2Service.getAccessToken(code);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("엑세스 토큰  "+accessToken);

        // STEP3: 토큰를 통해 사용자 정보 조회
        KakaoInfo kakaoInfo = null;
        try {
            kakaoInfo = oAuth2Service.getKakaoInfo(accessToken);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        //System.out.println("이메일 확인 "+kakaoInfo.getEmail());

        // STEP4: 카카오 사용자 정보 확인
        Member kakaoMember = oAuth2Service.ifNeedKakaoInfo(kakaoInfo);

        // STEP5: 강제 로그인
        // 세션에 회원 정보 저장 & 세션 유지 시간 설정
        if (kakaoMember != null) {
            SecurityUser securityUser = new SecurityUser(kakaoMember, kakaoMember.getUserId(), kakaoMember.getEmail(), "", kakaoMember.getAuthorities());

            Authentication authentication =
                    new OAuth2AuthenticationToken(securityUser, securityUser.getAuthorities(), LoginType.KAKAO.name());
            SecurityContextHolder.getContext().setAuthentication(authentication);


            // 새로운 세션 생성
            session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());

            session.setAttribute("loginMember", kakaoMember);
            // session.setMaxInactiveInterval( ) : 세션 타임아웃을 설정하는 메서드
            // 로그인 유지 시간 설정 (1800초 == 30분)
            session.setMaxInactiveInterval(60 * 30);
            // 로그아웃 시 사용할 카카오토큰 추가
            session.setAttribute("kakaoToken", accessToken);
        }

        return "redirect:/";
    }

    /**
     * 카카오 로그아웃
     */
    @GetMapping("/kakao/logout")
    public String kakaoLogout(HttpSession session) {
        String accessToken = (String) session.getAttribute("kakaoToken");

        if(accessToken != null && !"".equals(accessToken)){
            try {
                oAuth2Service.kakaoDisconnect(accessToken);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            session.removeAttribute("kakaoToken");
            session.removeAttribute("loginMember");
        }else{
            System.out.println("accessToken is null");
        }

        return "redirect:/";
    }

}
