package com.ll.JollyJourney.domain.member.member.dto.kakao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

//유저 정보 가져오기
@FeignClient(name = "kakaoInfoClient", url = "https://kapi.kakao.com")
public interface KakaoAuthClient {

    @PostMapping(value = "/v1/user/unlink", consumes = "application/x-www-form-urlencoded")
    void revoke(
            @RequestHeader(name = "Authorization")
            String Authorization,
            @RequestBody KakaoRevokeRequest kakaoRevokeRequest
    );

}
