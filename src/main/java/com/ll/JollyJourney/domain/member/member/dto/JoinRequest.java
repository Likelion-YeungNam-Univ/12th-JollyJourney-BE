package com.ll.JollyJourney.domain.member.member.dto;


import com.ll.JollyJourney.domain.member.member.entity.Member;
import com.ll.JollyJourney.domain.member.member.entity.MemberRole;
import com.ll.JollyJourney.global.enums.Gender;
import com.ll.JollyJourney.global.enums.LoginType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinRequest {

    @NotBlank(message = "이메일은 필수 항목입니다.")
    @Email(message = "올바른 이메일 형식을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    @Pattern(
            regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
            message = "영문자(A-Z, a-z), 숫자, 특수문자로 구성된 8~16자입니다."
    )
    private String password;

    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String passwordConfirm;

    @NotBlank(message = "본명은 필수 항목입니다.")
    private String name; // 본명

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    @Pattern(
            regexp = "^\\d{3}-\\d{4}-\\d{4}$",
            message = "전화번호를 다시 확인해주세요.(ex. 010-1234-1234)"
    )
    private String phoneNumber;

    @NotNull(message = "생년월일는 필수 항목입니다.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @NotNull(message = "성별 필수 항목입니다.")
    private Gender gender;

//    public Member toEntity() {
//        return Member.builder()
//                .email(this.email)
//                .password(this.password)
//                .name(this.name)
//                .phoneNumber(this.phoneNumber)
//                .birthDay(this.birthDay)
//                .gender(this.gender)
//                .role(MemberRole.MEMBER)
//                .loginType(LoginType.APP)
//                .build();
//    }
//}

    public Member toEntity(JoinRequest joinRequest) {
        return Member.builder()
                .email(joinRequest.email)
                .password(joinRequest.password)
                .name(joinRequest.name)
                .phoneNumber(joinRequest.phoneNumber)
                .birthDay(joinRequest.birthDay)
                .gender(joinRequest.gender)
                .role(MemberRole.MEMBER)
                .loginType(LoginType.APP)
                .build();
    }
}
