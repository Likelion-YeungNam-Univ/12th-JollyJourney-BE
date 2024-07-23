//package com.ll.JollyJourney.domain.member.member.service;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoAuthClient;
//import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoInfo;
//import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoRevokeRequest;
//import com.ll.JollyJourney.domain.member.member.entity.Member;
//import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
//import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
//import com.ll.JollyJourney.global.enums.Gender;
//import com.ll.JollyJourney.global.enums.LoginType;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class OAuth2Service {
//
//    @Value("${kakao.client.id}")
//    String clientId;
//    @Value("${kakao.redirect.uri}")
//    String redirectUri;
//    @Value("${kakao.client.secret}")
//    String clientSecret;
//    @Value("${kakao.admin}")
//    String adminKey;
//
//    private final KakaoAuthClient kakaoAuthClient;
//
//    private final MemberRepository memberRepository;
//
//    public String getAccessToken(String code) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP Body 생성
//        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
//        body.add("grant_type", "authorization_code");
//        body.add("client_id", clientId);
//        body.add("redirect_uri", redirectUri);
//        body.add("code", code);
//        body.add("client_secret", clientSecret);
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//
//        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//
//        return jsonNode.get("access_token").asText();
//    }
//
//    public Member ifNeedKakaoInfo (KakaoInfo kakaoInfo) {
//        // DB에 중복되는 email이 있는지 확인
//        String kakaoEmail = kakaoInfo.getEmail();
//        Optional<Member> _findMember = memberRepository.findByEmail(kakaoEmail);
//
//        Member member = _findMember.orElse(null);
//        // 회원가입
//        if (_findMember.isEmpty()) {
////            String kakaoNickname = kakaoInfo.getNickname();
////            // 이메일로 임시 id 발급
////            int idx= kakaoEmail.indexOf("@");
////            String kakaoId = kakaoEmail.substring(0, idx);
////            // 임시 password 발급 - random UUID
////            String tempPassword = UUID.randomUUID().toString();
//
//
//            member = Member.builder()
//                    .loginType(LoginType.KAKAO)
//                    .role(MemberRole.MEMBER)
//                    .email(kakaoInfo.getEmail())
//                    .phoneNumber(kakaoInfo.getPhoneNumber())
//                    .providerId(kakaoInfo.getId())
//                    .name(kakaoInfo.getName())
//                    .gender(Gender.MALE)
//                    .birthDay(kakaoInfo.getBirthday())
//                    .build();
//
//
//            member = memberRepository.save(member);
//        }
//
//        return member;
//    }
//
//    public KakaoInfo getKakaoInfo(String accessToken) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v2/user/me",
//                HttpMethod.POST,
//                kakaoUserInfoRequest,
//                String.class
//        );
//
//        // responseBody에 있는 정보 꺼내기
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//
//        String id = jsonNode.get("id").asText();
////        String name = jsonNode.get("kakao_account")
////                .get("name").asText();
//        String email = jsonNode.get("kakao_account").get("email").asText();
//        String nickname = jsonNode.get("kakao_account").get("profile").get("nickname").asText();
////        String gender = jsonNode.get("kakao_account")
////                .get("gender").asText();
////        String birthYear = jsonNode.get("kakao_account")
////                .get("birthyear").asText();
////        String birthMonthAndDay = jsonNode.get("kakao_account")
////                .get("birthday").asText();
////        String phoneNumber = jsonNode.get("kakao_account")
////                .get("phone_number").asText();
//
//        return new KakaoInfo(id, nickname, email, null, null, null, null);
//    }
//
//    public void kakaoDisconnect(String accessToken) throws JsonProcessingException {
//        // HTTP Header 생성
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Bearer " + accessToken);
//        headers.add("Content-type", "application/x-www-form-urlencoded");
//
//        // HTTP 요청 보내기
//        HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
//        RestTemplate rt = new RestTemplate();
//        ResponseEntity<String> response = rt.exchange(
//                "https://kapi.kakao.com/v1/user/logout",
//                HttpMethod.POST,
//                kakaoLogoutRequest,
//                String.class
//        );
//
//        // responseBody에 있는 정보를 꺼냄
//        String responseBody = response.getBody();
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(responseBody);
//
//        Long id = jsonNode.get("id").asLong();
//        System.out.println("반환된 id: "+id);
//    }
//
//    public void kakaoRevoke(String providerId) {
//        try {
//            System.out.println("clientSecret = " + adminKey);
//            KakaoRevokeRequest kakaoRevokeRequest = KakaoRevokeRequest.builder()
//                    .target_id_type("user_id")
//                    .target_id(providerId)
//                    .build();
//            kakaoAuthClient.revoke("KakaoAK " + adminKey, kakaoRevokeRequest);
//        } catch (HttpClientErrorException e) {
//            throw new RuntimeException("Kakao Revoke Error");
//        }
//    }
//}
package com.ll.JollyJourney.domain.member.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoAuthClient;
import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoInfo;
import com.ll.JollyJourney.domain.member.member.dto.kakao.KakaoRevokeRequest;
import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.domain.member.member.repository.MemberRepository;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.enums.LoginType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OAuth2Service {

    @Value("${kakao.client.id}")
    String clientId;
    @Value("${kakao.redirect.uri}")
    String redirectUri;
    @Value("${kakao.client.secret}")
    String clientSecret;
    @Value("${kakao.admin}")
    String adminKey;

    private final KakaoAuthClient kakaoAuthClient;
    private final MemberRepository memberRepository;

    public String getAccessToken(String code) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", clientId);
        body.add("redirect_uri", redirectUri);
        body.add("code", code);
        body.add("client_secret", clientSecret);

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.get("access_token").asText();
    }

    public Member ifNeedKakaoInfo(KakaoInfo kakaoInfo) {
        String kakaoEmail = kakaoInfo.getEmail();
        Optional<Member> _findMember = memberRepository.findByEmail(kakaoEmail);

        Member member = _findMember.orElse(null);
        if (_findMember.isEmpty()) {
            member = Member.builder()
                    .loginType(LoginType.KAKAO)
                    .role(MemberRole.MEMBER)
                    .email(kakaoInfo.getEmail())
                    .phoneNumber(kakaoInfo.getPhoneNumber())
                    .providerId(kakaoInfo.getId())
                    .name(kakaoInfo.getName())
                    .gender(Gender.MALE)
                    .birthDay(kakaoInfo.getBirthday())
                    .build();

            member = memberRepository.save(member);
        }

        return member;
    }

    public KakaoInfo getKakaoInfo(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String id = jsonNode.has("id") ? jsonNode.get("id").asText() : null;
        String email = jsonNode.has("kakao_account") && jsonNode.get("kakao_account").has("email") ? jsonNode.get("kakao_account").get("email").asText() : null;
        String nickname = jsonNode.has("kakao_account") && jsonNode.get("kakao_account").has("profile") && jsonNode.get("kakao_account").get("profile").has("nickname") ? jsonNode.get("kakao_account").get("profile").get("nickname").asText() : null;

        if (id == null || email == null || nickname == null) {
            throw new RuntimeException("Invalid Kakao user information received.");
        }

        return new KakaoInfo(id, nickname, email, null, null, null, null);
    }

    public void kakaoDisconnect(String accessToken) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> kakaoLogoutRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v1/user/logout",
                HttpMethod.POST,
                kakaoLogoutRequest,
                String.class
        );

        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        Long id = jsonNode.has("id") ? jsonNode.get("id").asLong() : null;
        if (id == null) {
            throw new RuntimeException("Failed to disconnect Kakao user.");
        }
        System.out.println("반환된 id: " + id);
    }

    public void kakaoRevoke(String providerId) {
        try {
            System.out.println("clientSecret = " + adminKey);
            KakaoRevokeRequest kakaoRevokeRequest = KakaoRevokeRequest.builder()
                    .target_id_type("user_id")
                    .target_id(providerId)
                    .build();
            kakaoAuthClient.revoke("KakaoAK " + adminKey, kakaoRevokeRequest);
        } catch (HttpClientErrorException e) {
            throw new RuntimeException("Kakao Revoke Error: " + e.getMessage());
        }
    }
}
