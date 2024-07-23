package com.ll.JollyJourney.domain.member.member.dto.kakao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class KakaoRevokeRequest {

    private String target_id_type;
    private String target_id;
}
