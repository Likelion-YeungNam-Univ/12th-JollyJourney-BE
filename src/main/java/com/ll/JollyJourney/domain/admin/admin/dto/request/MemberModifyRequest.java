package com.ll.JollyJourney.domain.admin.admin.dto.request;

import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.global.enums.Gender;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberModifyRequest {
    private Long id;
    private String email;
    private String name;
    private String nickname;
    private MemberRole role;
    private Gender gender;
}
