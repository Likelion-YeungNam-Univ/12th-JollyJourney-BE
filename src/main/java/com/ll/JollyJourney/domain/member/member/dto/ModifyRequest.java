package com.ll.JollyJourney.domain.member.member.dto;

import com.ll.JollyJourney.global.enums.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyRequest {
    private String email;

    private String password;

    private String passwordConfirm;

    @NotNull
    private String phoneNumber; // 010-1234-5678

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private String name; // 본명

    private Gender gender;
}
